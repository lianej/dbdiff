
#Project: dbdiff
---


###这是什么?

一个对比不同数据库实例ddl的工具, 以便于发现生产环境和其他环境的不同(例如索引)

###如何使用?

```shell
mvn clean package
java -jar target/dbdiff.jar -r localGit -o /local/git/repo/path -e qa,uat -d /path/to/db.json
```

###实现方式:

- 使用mysql语句获取ddl, 将ddl存储为sql文件
- 使用git来做ddl diff, 将不同环境的ddl列为不同的分支, 以此进行diff

                
