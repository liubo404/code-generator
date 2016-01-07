
web项目开发步骤：

- 客户需求分析

- 设计数据库文档，创建物理数据库

- Coding，设计MVC各层包结构

为提高生产率，此项目即为自动生成MVC各层 基本代码

# 使用说明

	1. 打开 src/main/resources/conf.properties，配置数据库连接和文件输入路径（根据当前系统设定）
	
	2. 打开 src/test/java/cn/effine/GeneratorTest.java,运行main方法即可
	
	3. 若生成的文件模板格式不合你的心意，你可以修改文件模板(src/main/resources/templates/*)