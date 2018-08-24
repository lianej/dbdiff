package com.thoughtworks.dbdiff.repository;

import com.thoughtworks.dbdiff.metadata.DDLData;

import java.io.IOException;
import java.util.List;

public interface DDLRepository {

    void storeData(List<DDLData> ddlData);

}
