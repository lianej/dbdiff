
DB-diff
---


### 这是什么?

一个对比不同数据库实例ddl的工具, 以便于发现生产环境和其他环境的不同(例如索引)

### 如何使用?

这是一个命令行工具, 需要先使用maven进行打包, 然后使用java -jar来执行
```shell
mvn clean package
java -jar target/dbdiff.jar -o /local/git/repo/path -e qa,uat -d /path/to/db.json
```
命令会将不同环境数据库实例的ddl以git branch的形式进行导出, 然后使用vscode, idea或者dbdiff等工具来进行对比.

参数:
```
-r  指定仓库类型, 默认为localGit, 使用本地git仓库来存储导出的ddl
-o  输出目录, 即git仓库所在目录
-d  定义数据库环境的json格式配置文件, 见db.json.template 
-e  要对比的环境, 使用逗号分隔
```

### 实现方式:

- 使用mysql语句获取ddl, 将ddl存储为sql文件
- 使用git来做ddl diff, 将不同环境的ddl列为不同的分支, 以此进行diff

                
