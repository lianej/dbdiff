
DB-diff
---


### 这是什么?

一个对比不同数据库实例ddl的工具, 以便于发现生产环境和其他环境的不同(例如索引)

### 如何使用?

这是一个命令行工具, 需要先使用maven进行打包, 然后使用java -jar来执行
```shell
mvn clean package
mvn clean package && java -jar target/dbdiff.jar -r localGit -o /output-path -e qa,uat
```
命令会将不同环境数据库实例的ddl以git branch的形式进行导出, 并处理类似auto-id不同等差异

导出之后，可以使用vscode, idea或者dbdiff等工具来进行对比.（这部分需要手动执行...）


参数:
```
-r  指定仓库类型, 默认为localGit, 使用本地git仓库来存储导出的ddl
-o  输出目录, 即git仓库所在目录，默认情况下，在临时目录创建一个git仓库
-d  定义数据库环境的json格式配置文件, 见db.json.template 
-e  要对比的环境, 使用逗号分隔
```

                
