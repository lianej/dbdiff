package com.thoughtworks.dbdiff;

import com.thoughtworks.dbdiff.args.CommandParser;
import com.thoughtworks.dbdiff.args.DBDiffArguments;
import com.thoughtworks.dbdiff.metadata.MetadataReader;
import com.thoughtworks.dbdiff.repository.DDLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Application {

  public static void main(String[] args) {
    DBDiffArguments arguments = new CommandParser().parse(args);
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.getBeanFactory().registerSingleton("dbDiffArgs", arguments);
    context.scan("com.thoughtworks.dbdiff");
    context.refresh();
    context.getBean(Application.class).doDiff();
  }

  @Autowired private ApplicationContext context;

  @Autowired private DBDiffArguments arguments;

  public void doDiff() {
    DDLRepository ddlRepository = context.getBean(arguments.getRepoType(), DDLRepository.class);
    MetadataReader metadataReader = context.getBean(MetadataReader.class);
    arguments.getEnv().stream().map(metadataReader::readDDL).forEach(ddlRepository::storeData);
  }
}
