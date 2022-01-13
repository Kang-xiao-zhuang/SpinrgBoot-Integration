# Elasticsearch学习笔记

# 1.Elasticsearch 概述

## 1.1Elasticsearch 是什么

![在这里插入图片描述](https://img-blog.csdnimg.cn/07946f928c894bb2b7952cabde2d040d.png)

The Elastic Stack, 包括 Elasticsearch、Kibana、Beats 和 Logstash（也称为 ELK Stack）。 能够安全可靠地获取任何来源、任何格式的数据，然后实时地对数据进行搜索、分析和可视 化。Elaticsearch，简称为 ES，**ES 是一个开源的高扩展的分布式全文搜索引擎**，是整个 Elastic  Stack 技术栈的核心。它可以近乎实时的存储、检索数据；本身扩展性很好，可以扩展到上 百台服务器，处理 PB 级别的数据。

## 1.2 全文搜索引擎

Google，百度类的网站搜索，它们都是根据网页中的关键字生成索引，我们在搜索的时 候输入关键字，它们会将该关键字即索引匹配到的所有网页返回；还有常见的项目中应用日 志的搜索等等。对于这些非结构化的数据文本，关系型数据库搜索不是能很好的支持。

一般传统数据库，全文检索都实现的很鸡肋，因为一般也没人用数据库存文本字段。进 行全文检索需要扫描整个表，如果数据量大的话即使对 SQL 的语法优化，也收效甚微。建 立了索引，但是维护起来也很麻烦，对于 insert 和 update 操作都会重新构建索引。

基于以上原因可以分析得出，在一些生产环境中，使用常规的搜索方式，**性能是非常差的**

- 搜索的数据对象是大量的非结构化的文本数据。

- 文件记录量达到数十万或数百万个甚至更多。

- 支持大量基于交互式文本的查询。

- 需求非常灵活的全文搜索查询。

- 对高度相关的搜索结果的有特殊需求，但是没有可用的关系数据库可以满足。

- 对不同记录类型、非文本数据操作或安全事务处理的需求相对较少的情况。

这里说到的全文搜索引擎指的是目前广泛应用的主流搜索引擎。它的工作原理是计算机 索引程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的 次数和位置，当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反 馈给用户的检索方式。这个过程类似于通过字典中的检索字表查字的过程

## 1.3  Elasticsearch 应用案例

-  GitHub: 2013 年初，抛弃了 Solr，采取 Elasticsearch 来做 PB 级的搜索。“GitHub 使用 Elasticsearch 搜索 20TB 的数据，包括 13 亿文件和 1300 亿行代码”。

- 维基百科：启动以 Elasticsearch 为基础的核心搜索架构

- SoundCloud：“SoundCloud 使用 Elasticsearch 为 1.8 亿用户提供即时而精准的音乐搜索 服务”。

-  百度：目前广泛使用 Elasticsearch 作为文本数据分析，采集百度所有服务器上的各类指 标数据及用户自定义数据，通过对各种数据进行多维分析展示，辅助定位分析实例异常 或业务层面异常。目前覆盖百度内部 20 多个业务线（包括云分析、网盟、预测、文库、 直达号、钱包、风控等），单集群最大 100 台机器，200 个 ES 节点，每天导入 30TB+ 数据。  新浪：使用 Elasticsearch 分析处理 32 亿条实时日志。

- 阿里：使用 Elasticsearch 构建日志采集和分析体系。

-  Stack Overflow：解决 Bug 问题的网站，全英文，编程人员交流的网站。

#  2. Elasticsearch 入门

## 2.1 Elasticsearch 安装

Elasticsearch 的官方地址：https://www.elastic.co/cn/

下载地址：https://www.elastic.co/cn/downloads/past-releases#elasticsearch

Windows 版的 Elasticsearch 的安装很简单，解压即安装完毕，解压后的 Elasticsearch 的

![在这里插入图片描述](https://img-blog.csdnimg.cn/eddb501b821e441d92697b0110190457.png)


![在这里插入图片描述](https://img-blog.csdnimg.cn/c524af50b51e4469b2793edb9047de09.png)

解压后，进入 bin 文件目录，点击 elasticsearch.bat 文件启动 ES 服务

![在这里插入图片描述](https://img-blog.csdnimg.cn/3d35c4110d6c4060806456e108254d63.png)

注意：**9300** 端口为 Elasticsearch 集群间组件的通信端口，**9200** 端口为浏览器访问的 http 协议 RESTful 端口。

打开浏览器（推荐使用谷歌浏览器），输入地址：http://localhost:9200，测试结果

![在这里插入图片描述](https://img-blog.csdnimg.cn/b00bc01617974a7b944ca6df6b702c9a.png)

## 2.2 Elasticsearch 基本操作

REST 指的是一组架构约束条件和原则。满足这些约束条件和原则的应用程序或设计就 是 RESTful。Web 应用程序最重要的 REST 原则是，客户端和服务器之间的交互在请求之 间是无状态的。从客户端到服务器的每个请求都必须包含理解请求所必需的信息。如果服务 器在请求之间的任何时间点重启，客户端不会得到通知。此外，无状态请求可以由任何可用 服务器回答，这十分适合云计算之类的环境。客户端可以缓存数据以改进性能。

在服务器端，应用程序状态和功能可以分为各种资源。资源是一个有趣的概念实体，它 向客户端公开。资源的例子有：应用程序对象、数据库记录、算法等等。每个资源都使用 URI  (Universal Resource Identifier) 得到一个唯一的地址。所有资源都共享统一的接口，以便在客 户端和服务器之间传输状态。使用的是标准的 HTTP 方法，比如 GET、PUT、POST 和 DELETE。

在 REST 样式的 Web 服务中，每个资源都有一个地址。资源本身都是方法调用的目 标，方法列表对所有资源都是一样的。这些方法都是标准方法，包括 HTTP GET、POST、 PUT、DELETE，还可能包括 HEAD 和 OPTIONS。简单的理解就是，如果想要访问互联 网上的资源，就必须向资源所在的服务器发出请求，请求体中必须包含资源的网络路径，以 及对资源进行的操作(增删改查)。

## 2.3 客户端安装

如果直接通过浏览器向 Elasticsearch 服务器发请求，那么需要在发送的请求中包含 HTTP 标准的方法，而 HTTP 的大部分特性且仅支持 GET 和 POST 方法。所以为了能方便 地进行客户端的访问，可以使用 Postman 软件 Postman 是一款强大的网页调试工具，提供功能强大的 Web API 和 HTTP 请求调试。 软件功能强大，界面简洁明晰、操作方便快捷，设计得很人性化。Postman 中文版能够发送 任何类型的 HTTP 请求 (GET, HEAD, POST, PUT..)，不仅能够表单提交，且可以附带任意 类型请求体

Postman 官网：https://www.getpostman.com Postman 下载：https://www.getpostman.com/apps

## 2.4 数据格式

Elasticsearch 是面向文档型数据库，一条数据在这里就是一个文档。为了方便大家理解， 我们将 Elasticsearch 里存储文档数据和关系型数据库 MySQL 存储数据的概念进行一个类比

![在这里插入图片描述](https://img-blog.csdnimg.cn/6e93811ee25f47d99b73dec17e7b61c7.png)

ES 里的 Index 可以看做一个库，而 Types 相当于表，Documents 则相当于表的行。 这里 Types 的概念已经被逐渐弱化，Elasticsearch 6.X 中，一个 index 下已经只能包含一个 type，Elasticsearch 7.X 中, Type 的概念已经被删除了。

```json
{
 "name" : "John",
 "sex" : "Male",
 "age" : 25,
 "birthDate": "1990/05/01",
 "about" : "I love to go rock climbing",
 "interests": [ "sports", "music" ]
}
```

# 3. HTTP 操作

## 3.1 索引操作

**创建索引**

对比关系型数据库，创建索引就等同于创建数据库 在 Postman 中，向 ES 服务器发 PUT 请求 ：http://127.0.0.1:9200/shopping

![在这里插入图片描述](https://img-blog.csdnimg.cn/e71c4063973d4595bdcbf16f240b0df2.png)

```json
{
 "acknowledged"【响应结果】: true, # true 操作成功
 "shards_acknowledged"【分片结果】: true, # 分片操作成功
 "index"【索引名称】: "shopping"
}
# 注意：创建索引库的分片数默认 1 片，在 7.0.0 之前的 Elasticsearch 版本中，默认 5 片
```

如果重复添加索引，会返回错误信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/99b2e961ab58407a8e5196d1e0399c84.png)

**查看所有索引**

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/_cat/indices?v

请求路径中的_cat 表示查看的意思，indices 表示索引，所以整体含义就是查看当前 ES 服务器中的所有索引，就好像 MySQL 中的 show tables 的感觉，服务器响应结果如下

![在这里插入图片描述](https://img-blog.csdnimg.cn/82714bd9924b4b31931c69058bc3a4ec.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/3856e8d5374e4158ba7ef469489291ad.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**查看单个索引**

在 Postman 中，向 ES 服务器发 **GET** 请求 ：http://127.0.0.1:9200/shopping

查看索引向 ES 服务器发送的请求路径和创建索引是一致的。但是 HTTP 方法不一致。这里 可以体会一下 RESTful 的意义， 请求后，服务器响应结果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/a00862e71792466b8fb013ae0508a2e1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"shopping"【
	索引名】: {
		"aliases"【
		别名】: {},
		"mappings"【
		映射】: {},
		"settings"【
		设置】: {
			"index"【
			设置 - 索引】: {
				"creation_date"【
				设置 - 索引 - 创建时间】: "1614265373911",
				"number_of_shards"【
				设置 - 索引 - 主分片数量】: "1",
				"number_of_replicas"【
				设置 - 索引 - 副分片数量】: "1",
				"uuid"【
				设置 - 索引 - 唯一标识】: "eI5wemRERTumxGCc1bAk2A",
				"version"【
				设置 - 索引 - 版本】: {
					"created": "7080099"
				},
				"provided_name"【
				设置 - 索引 - 名称】: "shopping"
			}
		}
	}
}
```

**删除索引**

在 Postman 中，向 ES 服务器发 **DELETE** 请求 ：http://127.0.0.1:9200/shopping

![在这里插入图片描述](https://img-blog.csdnimg.cn/de17451a167444f0a6cb8ad34736c4ab.png)

重新访问索引时，服务器返回响应：索引不存在

![在这里插入图片描述](https://img-blog.csdnimg.cn/8c3627eda81e4f62bcf383232012ae25.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

# 4. 文档操作

**创建文档**

索引已经创建好了，接下来我们来创建文档，并添加数据。这里的文档可以类比为关系型数 据库中的表数据，添加的数据格式为 JSON 格式

在 Postman 中，向 ES 服务器发 **POST** 请求 ：http://127.0.0.1:9200/shopping/_doc

```json
{
 "title":"小米手机",
 "category":"小米",
 "images":"http://www.gulixueyuan.com/xm.jpg",
 "price":3999.00
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/ed7aed5bb01840db907a407e17d346d5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

此处发送请求的方式必须为 **POST**，不能是 **PUT**，否则会发生错误

​	![在这里插入图片描述](https://img-blog.csdnimg.cn/25932708e6a142208cad22455263b9e4.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/171d30aef5cf45178d239a292a687948.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"_index"【
	索引】: "shopping",
		"_type"【
	类型 - 文档】: "_doc",
		"_id"【
	唯一标识】: "Xhsa2ncBlvF_7lxyCE9G", #可以类比为 MySQL 中的主键， 随机生成 "_version"【
	版本】: 1,
		"result"【
	结果】: "created", #这里的 create 表示创建成功 "_shards"【
	分片】: {
			"total"【
			分片 - 总数】: 2,
			"successful"【
			分片 - 成功】: 1,
			"failed"【
			分片 - 失败】: 0
		},
		"_seq_no": 0,
		"_primary_term": 1
}
```

上面的数据创建后，由于没有指定数据唯一性标识（ID），默认情况下，ES 服务器会随机 生成一个。 如果想要自定义唯一性标识，需要在创建时指定：http://127.0.0.1:9200/shopping/_doc/1

![在这里插入图片描述](https://img-blog.csdnimg.cn/52e284473d924c938b46077ec48fd9fb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/62a2580800864f61af786bf88bf7f0e2.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**查看文档**

查看文档时，需要指明文档的唯一性标识，类似于 MySQL 中数据的主键查询 在 Postman 中，向 ES 服务器发 **GET** 请求 ：http://127.0.0.1:9200/shopping/_doc/1

查询成功后，服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/5fa3b55e7db8455986ca72f6cf3ff68a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"_index"【
	索引】: "shopping",
		"_type"【
	文档类型】: "_doc",
		"_id": "1",
		"_version": 2,
		"_seq_no": 2,
		"_primary_term": 2,
		"found"【
	查询结果】: true, #true 表示查找到， false 表示未查找到 "_source"【
	文档源信息】: {
		"title": "华为手机",
		"category": "华为",
		"images": "http://www.gulixueyuan.com/hw.jpg",
		"price": 4999.00
	}
}
```

**修改文档**

和新增文档一样，输入相同的 URL 地址请求，如果请求体变化，会将原有的数据内容覆盖 在 Postman 中，向 ES 服务器发 POST 请求 ：http://127.0.0.1:9200/shopping/_doc/1 请求体内容为

```json
{
 "title":"华为手机",
 "category":"华为",
 "images":"http://www.gulixueyuan.com/hw.jpg",
 "price":4999.00
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/150aada155054a8fb831a79bb0745621.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

修改成功后，服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/e0fb5b15278343059b9b2feeaf84bf61.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"_index": "shopping",
	"_type": "_doc",
	"_id": "1",
	"_version"【
	版本】: 2,
	"result"【
	结果】: "updated",
	#updated 表示数据被更新 "_shards": {
		"total": 2,
		"successful": 1,
		"failed": 0
	},
	"_seq_no": 2,
	"_primary_term": 2
}
```

**修改字段**

修改数据时，也可以只修改某一给条数据的局部信息

在 Postman 中，向 ES 服务器发 POST 请求 ：http://127.0.0.1:9200/shopping/_update/1 请求体内容为：

```json
{
	"doc": {
		"price": 3000.00
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b97122cc473a4247a164d5a9ec41a0de.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

修改成功后，服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/dd501cd8772d45e9a086fefd83b3bf75.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

根据唯一性标识，查询文档数据，文档数据已经更新

![在这里插入图片描述](https://img-blog.csdnimg.cn/bc735af889b945e0a7bbc832f3683e63.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**删除文档**

删除一个文档不会立即从磁盘上移除，它只是被标记成已删除（逻辑删除）。 在 Postman 中，向 **ES** 服务器发 **DELETE** 请求 ：http://127.0.0.1:9200/shopping/phone/1

![在这里插入图片描述](https://img-blog.csdnimg.cn/6ccef520d7074f0a97a57b5a5d9647bc.png)

删除成功，服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/b330984ac080474099b81122aa8c60e5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"_index": "shopping",
	"_type": "_doc",
	"_id": "1",
	"_version"【
	版本】: 4,
	#对数据的操作， 都会更新版本 "result"【
	结果】: "deleted",
	#deleted 表示数据被标记为删除 "_shards": {
		"total": 2,
		"successful": 1,
		"failed": 0
	},
	"_seq_no": 4,
	"_primary_term": 2
}
```

删除后再查询当前文档信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/4f4c06db6651498bbecf4fb24dafe477.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

如果删除一个并不存在的文档

![在这里插入图片描述](https://img-blog.csdnimg.cn/781781bbe13d4f1081183e7bfcd87264.png)

```json
{
	"_index": "shopping",
	"_type": "_doc",
	"_id": "1",
	"_version": 1,
	"result"【
	结果】: "not_found",
	#not_found 表示未查找到 "_shards": {
		"total": 2,
		"successful": 1,
		"failed": 0
	},
	"_seq_no": 5,
	"_primary_term": 2
}
```

**条件删除文档**

一般删除数据都是根据文档的唯一性标识进行删除，实际操作时，也可以根据条件对多条数 据进行删除 首先分别增加多条数据:

```java
{
	"title": "小米手机",
	"category": "小米",
	"images": "http://www.gulixueyuan.com/xm.jpg",
	"price": 4000.00
} {
	"title": "华为手机",
	"category": "华为",
	"images": "http://www.gulixueyuan.com/hw.jpg",
	"price": 4000.00
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8c432836e6df455892be143cf37951f7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

向 ES 服务器发 POST 请求 ：http://127.0.0.1:9200/shopping/_delete_by_query

请求体内容为：

```json
{
	"query": {
		"match": {
			"price": 4000.00
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/6ef109d793494b938b3981ccc20dd87c.png)

删除成功后，服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/7f87283f2ea84337b0d6970ccdd98622.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"took"【
	耗时】: 175,
		"timed_out"【
	是否超时】: false,
		"total"【
	总数】: 2,
		"deleted"【
	删除数量】: 2,
		"batches": 1,
		"version_conflicts": 0,
		"noops": 0,
		"retries": {
			"bulk": 0,
			"search": 0
		},
		"throttled_millis": 0,
		"requests_per_second": -1.0,
		"throttled_until_millis": 0,
		"failures": []
}
```

**映射操作**

有了索引库，等于有了数据库中的 database。

接下来就需要建索引库(index)中的映射了，类似于数据库(database)中的表结构(table)。 创建数据库表需要设置字段名称，类型，长度，约束等；索引库也一样，需要知道这个类型 下有哪些字段，每个字段有哪些约束信息，这就叫做映射(mapping)。

**创建映射**

在 Postman 中，向 ES 服务器发 PUT 请求 ：http://127.0.0.1:9200/student/_mapping

请求体内容为：

```json
{
	"properties": {
		"name": {
			"type": "text",
			"index": true
		},
		"sex": {
			"type": "text",
			"index": false
		},
		"age": {
			"type": "long",
			"index": false
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/efe543417dc14159abc4961ef5b66e51.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/d3d7f534a16c4b5e94894827ed3d4384.png)

映射数据说明：

- 字段名：任意填写，下面指定许多属性，例如：title、subtitle、images、price

- type：类型，Elasticsearch 中支持的数据类型非常丰富，说几个关键的：
    - String 类型，又分两种：
        - text：可分词
        - keyword：不可分词，数据会作为完整字段进行匹配
    - Numerical：数值类型，分两类
        - 基本数据类型：long、integer、short、byte、double、float、half_float
        - 浮点数的高精度类型：scaled_float
    - Date：日期类型
    - Array：数组类型
    - Object：对象
- index：是否索引，默认为 true，也就是说你不进行任何配置，所有字段都会被索引
    - index：是否索引，默认为 true，也就是说你不进行任何配置，所有字段都会被索引。
    - false：字段不会被索引，不能用来搜索
- store：是否将数据进行独立存储，默认为 false

> 原始的文本会存储在_source 里面，默认情况下其他提取出来的字段都不是独立存储 的，是从_source 里面提取出来的。当然你也可以独立的存储某个字段，只要设置 "store": true 即可，获取独立存储的字段要比从_source 中解析快得多，但是也会占用 更多的空间，所以要根据实际业务需求来设置

- analyzer：分词器

**查看映射**

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_mapping

![在这里插入图片描述](https://img-blog.csdnimg.cn/55a765e574ee4e25a9119e51bc94a223.png)

服务器响应结果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/09e47ae73e544065bf6558f59670df98.png)

**索引映射关联**

在 Postman 中，向 ES 服务器发 PUT 请求 ：http://127.0.0.1:9200/student1

```json
{
	"settings": {},
	"mappings": {
		"properties": {
			"name": {
				"type": "text",
				"index": true

			},
			"sex": {
				"type": "text",
				"index": false
			},
			"age": {
				"type": "long",
				"index": false
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/f75c6634333746f2b5934aefbdc520d3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/8243f11e2cd844a092fe65aed4a2103e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

# 5. 高级查询

Elasticsearch 提供了基于 JSON 提供完整的查询 DSL 来定义查询 定义数据 :

```json
# POST /student/_doc/1001
{
"name":"zhangsan",
"nickname":"zhangsan",
 "sex":"男",
 "age":30
}
# POST /student/_doc/1002
{
"name":"lisi",
"nickname":"lisi",
 "sex":"男",
 "age":20
}
# POST /student/_doc/1003
{
"name":"wangwu",
 "nickname":"wangwu",
 "sex":"女",
 "age":40
}
# POST /student/_doc/1004
{
"name":"zhangsan1",
"nickname":"zhangsan1",
 "sex":"女",
 "age":50
}
# POST /student/_doc/1005
{
"name":"zhangsan2",
"nickname":"zhangsan2",
 "sex":"女",
 "age":30
}
```

**查询所有文档**

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
 "query": {
 "match_all": {}
 }
}
# "query"：这里的 query 代表一个查询对象，里面可以有不同的查询属性
# "match_all"：查询类型，例如：match_all(代表查询所有)， match，term ， range 等等
# {查询条件}：查询条件会根据类型的不同，写法也有差异
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/68303d1c56424021a26193a92eb5bb43.png)

服务器响应结果如下

![在这里插入图片描述](https://img-blog.csdnimg.cn/aae3d7dc5d78423bb774b9ad7b4035d7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/89c84642fbf54f7cb54784674910c697.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```json
{
	"took【查询花费时间，单位毫秒】": 1116,
	"timed_out【是否超时】": false,
	"_shards【分片信息】": {
		"total【总数】": 1,
		"successful【成功】": 1,
		"skipped【忽略】": 0,
		"failed【失败】": 0
	},
	"hits【搜索命中结果】": {
		"total"【
		搜索条件匹配的文档总数】: {
			"value"【
			总命中计数的值】: 3,
			"relation"【
			计数规则】: "eq"#
			eq 表示计数准确， gte 表示计数不准确
		},
		"max_score【匹配度分值】": 1.0,
		"hits【命中结果集合】": [。。。
		}
	]
}
}
```

**匹配查询**

match 匹配类型查询，会把查询条件进行分词，然后进行查询，多个词条之间是 or 的关系 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match": {
			"name": "zhangsan"
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b20a9f321bc443038a06655c5ff78eca.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果为：

![在这里插入图片描述](https://img-blog.csdnimg.cn/66f7fe55a7fa4910822800565f6170e9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**字段匹配查询**

multi_match 与 match 类似，不同的是它可以在多个字段中查询。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"multi_match": {
			"query": "zhangsan",
			"fields": ["name", "nickname"]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/64681cb617de470894af5e79ac6f513a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/d6efe8f08dcd41ae8cefe4c0dc789378.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**关键字精确查询**

term 查询，精确的关键词匹配查询，不对查询条件进行分词。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"term": {
			"name": {
				"value": "zhangsan"
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/45551cc8ffda4a7aa6d6b5549972e58c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/c4f064c4ed1943fc92fa65084c4e2706.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**多关键字精确查询**

terms 查询和 term 查询一样，但它允许你指定多值进行匹配。 如果这个字段包含了指定值中的任何一个值，那么这个文档满足条件，类似于 mysql 的 in 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"terms": {
			"name": ["zhangsan", "lisi"]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/23ef1777f9ef44f794ac5948460d9a03.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/604ce82a9af648ac99a6bcd2c7e641a3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**指定查询字段**

默认情况下，Elasticsearch 在搜索的结果中，会把文档中保存在_source 的所有字段都返回。 如果我们只想获取其中的部分字段，我们可以添加_source 的过滤 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"_source": ["name", "nickname"],
	"query": {
		"terms": {
			"nickname": ["zhangsan"]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/124cb7dbb6d64dffb477885812b3bb88.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/b8339532ab6049b08d9b482372213377.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**过滤字段**

includes：来指定想要显示的字段

excludes：来指定不想要显示的字段

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"_source": {
		"includes": ["name", "nickname"]
	},
	"query": {
		"terms": {
			"nickname": ["zhangsan"]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/ec210a95155f4a60adab60b580f799d9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/1fc0742f197f44069e23446cf5e6b427.png)

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"_source": {
		"excludes": ["name", "nickname"]
	},
	"query": {
		"terms": {
			"nickname": ["zhangsan"]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/07fe7e82fd8e45e0aa9cb4fb8379595c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/05892a20a9b14a7fae376bcbb113b8b7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**组合查询**

`bool`把各种其它查询通过`must`（必须 ）、`must_not`（必须不）、`should`（应该）的方 式进行组合 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"bool": {
			"must": [{
				"match": {
					"name": "zhangsan"
				}
			}],
			"must_not": [{
				"match": {
					"age": "40"
				}
			}],
			"should": [{
				"match": {
					"sex": "男"
				}
			}]
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/53dabdf8d91c4e408822011374c496ce.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果

![在这里插入图片描述](https://img-blog.csdnimg.cn/76051104a4fa4fd6a3f145f97c3b53ab.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**范围查询**

range 查询找出那些落在指定区间内的数字或者时间。range 查询允许以下字符

![在这里插入图片描述](https://img-blog.csdnimg.cn/5c5db60e9ba747d29fd14a87a112d014.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"range": {
			"age": {
				"gte": 30,
				"lte": 35
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/121ef58b003c456ba44b04b02b8c9efb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/92947ff30ffb48c29ca52c65e1891503.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**模糊查询**

返回包含与搜索字词相似的字词的文档。 编辑距离是将一个术语转换为另一个术语所需的一个字符更改的次数。这些更改可以包括：

- 更改字符（box → fox）
- 删除字符（black → lack）
- 插入字符（sic → sick）
- 转置两个相邻字符（act → cat）

为了找到相似的术语，fuzzy 查询会在指定的编辑距离内创建一组搜索词的所有可能的变体 或扩展。然后查询返回每个扩展的完全匹配。 通过 fuzziness 修改编辑距离。一般使用默认值 AUTO，根据术语的长度生成编辑距离。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"fuzzy": {
			"title": {
				"value": "zhangsan"
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/616a072ed3f84a6ba685f080ebac1f56.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/0d1a2a89f35746ebb2e9e9aa7c800161.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"fuzzy": {
			"title": {
				"value": "zhangsan",
				"fuzziness": 2
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/722bdf9dcd6744c587c467fb96e872a9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/3d5cfbd81f5f44f0bc4724820eb610aa.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**单字段排序**

sort 可以让我们按照不同的字段进行排序，并且通过 order 指定排序的方式。desc 降序，asc 升序。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match": {
			"name": "zhangsan"
		}
	},
	"sort": [{
		"age": {
			"order": "desc"
		}
	}]
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/5ae74728b80a4ca99057e45906c9bc76.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/dcbf08f92d544b82af62c40f11f8ed69.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**模糊查询**

返回包含与搜索字词相似的字词的文档。 编辑距离是将一个术语转换为另一个术语所需的一个字符更改的次数。这些更改可以包括：

- 更改字符（box → fox）

- 删除字符（black → lack）

- 插入字符（sic → sick）

- 转置两个相邻字符（act → cat）

为了找到相似的术语，fuzzy 查询会在指定的编辑距离内创建一组搜索词的所有可能的变体 或扩展。然后查询返回每个扩展的完全匹配。 通过 fuzziness 修改编辑距离。一般使用默认值 AUTO，根据术语的长度生成编辑距离。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"fuzzy": {
			"title": {
				"value": "zhangsan"
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/e67e50d2256e4b6091ee4e6446b6ef35.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/7cafa7149c3f4c3db4e2c7785ab63a29.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"fuzzy": {
			"title": {
				"value": "zhangsan",
				"fuzziness": 2
			}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2b951b4a52894c73b0dd6ed89858fc68.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/8a42e226a0c34b229f9233086d15b086.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**单字段排序**

sort 可以让我们按照不同的字段进行排序，并且通过 order 指定排序的方式。desc 降序，asc 升序。 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match": {
			"name": "zhangsan"
		}
	},
	"sort": [{
		"age": {
			"order": "desc"
		}
	}]
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/784ff95dd2b847079a7b182950e8cd09.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/5321509826d24f208132b7f46ce4ab0d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**多字段排序**

假定我们想要结合使用 age 和 score 进行查询，并且匹配的结果首先按照年龄排序，然后 按照相关性得分排序 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match_all": {}
	},
	"sort": [{
			"age": {
				"order": "desc"
			}
		},
		{
			"_score": {
				"order": "desc"
			}
		}
	]
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/ff74f182dde64bfb9c894d0c118dbcaf.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/d7e7c95ad0cc4c0b9028c5d887979abc.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**高亮查询**

在进行关键字搜索时，搜索出的内容中的关键字会显示不同的颜色，称之为高亮

![在这里插入图片描述](https://img-blog.csdnimg.cn/e1c2912956ce4c00b00a5b687882892b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

Elasticsearch 可以对查询内容中的关键字部分，进行标签和样式(高亮)的设置。 在使用 match 查询的同时，加上一个 highlight 属性：

- pre_tags：前置标签

- post_tags：后置标签

- fields：需要高亮的字段

- title：这里声明 title 字段需要高亮，

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match": {
			"name": "zhangsan"
		}
	},
	"highlight": {
		"pre_tags": "<font color='red'>",
		"post_tags": "</font>",
		"fields": {
			"name": {}
		}
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/7a750333db0444b4ae91dfbd020c2ba5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果

![在这里插入图片描述](https://img-blog.csdnimg.cn/df9e5b783da149a2b02f5074a4740e99.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**分页查询**

from：当前页的起始索引，默认从 0 开始。 from = (pageNum - 1) * size size：每页显示多少条 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"query": {
		"match_all": {}
	},
	"sort": [{
		"age": {
			"order": "desc"
		}
	}],
	"from": 0,
	"size": 2
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/0cb3d4f4846e44878f2f9b18d132c844.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/45a2c52faa23415596514ad8ba8b094f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**聚合查询**

聚合允许使用者对 es 文档进行统计分析，类似与关系型数据库中的 group by，当然还有很 多其他的聚合，例如取最大值、平均值等等。

- 对某个字段取最大值 max

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"max_age": {
			"max": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/a67d8ae3115a439ab7a9cd6331231963.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/398bf891b05d4ae6b56ced6208b1c2e0.png)

- 对某个字段取最小值 min

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"min_age": {
			"min": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/c18d984e20e346148a58059de5f6c3e2.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/ffd96e6312824a6d84f8f126ee2c3767.png)

- 对某个字段求和 sum

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"sum_age": {
			"sum": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/37dcca2e869d4392b6a4ceb89d0a82c4.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/6510055c8fc84df3b631898a6e387dd6.png)

- 对某个字段取平均值 avg

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"avg_age": {
			"avg": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/6504cceb8da7481dac262fc4ffedf2b3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/96a2555da45145a19528db9fc1e2b990.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

- 对某个字段的值进行去重之后再取总数

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```java
{
	"aggs": {
		"distinct_age": {
			"cardinality": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/5260fff9ff40411fa8ad0813bcfb4d7e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/8b1ccbcb236240d29951e244b65c7800.png)

**State 聚合**

stats 聚合，对某个字段一次性返回 count，max，min，avg 和 sum 五个指标 在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"stats_age": {
			"stats": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/31ba8a1d9dd94b6e89d7fc2971373004.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/5746b38a422947fc889d273af44e28c5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**桶聚合查询**

桶聚和相当于 sql 中的 group by 语句

- terms 聚合，分组统计

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"age_groupby": {
			"terms": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/5363838a4bf14e80ae21c4f8cc28541b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/237a1733cd174304be7fa0177dfdc7ef.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

- 在 terms 分组下再进行聚合

在 Postman 中，向 ES 服务器发 GET 请求 ：http://127.0.0.1:9200/student/_search

```json
{
	"aggs": {
		"age_groupby": {
			"terms": {
				"field": "age"
			}
		}
	},
	"size": 0
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/3dee4a6e170e4b2eaa3d8bcfa8844ec8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

服务器响应结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/61e975b1e5e14ea1ba80ecf0b81c2eec.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

# 6. Java API 操作

```xml
<properties>
        <java.version>1.8</java.version>
        <elasticsearch.version>7.8.0</elasticsearch.version>
        <swagger2.version>2.9.2</swagger2.version>
    </properties>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.0</version>
        <relativePath/>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.71</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.8.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.8.2</version>
        </dependency>
        <!--swagger-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger2.version}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.10.2</version>
        </dependency>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.0.3</version>
        </dependency>
    </dependencies>
```

## 6.1 客户端对象

代码中创建 Elasticsearch 客户端对象 因为早期版本的客户端对象已经不再推荐使用，且在未来版本中会被删除，所以这里我们采 用高级 REST 客户端对象

```java
package com.zhuang.springbootelasticsearch.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Client
 * @Description 创建本地的es客户端
 * @Date 2021/12/23 20:21
 * @Author by dell
 */
public class ESTest_Client {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        // 关闭ES客户端
        esClient.close();
    }
}
```

注意：9200 端口为 Elasticsearch 的 Web 通信端口，localhost 为启动 ES 服务的主机名

## 6.2 索引操作

ES 服务器正常启动后，可以通过 Java API 客户端对象对 ES 索引进行操作

**工具类**

```java
package com.zhuang.springbootelasticsearch.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Client
 * @Description 创建本地的es客户端
 * @Date 2021/12/23 20:21
 * @Author by dell
 */
public class ESTest_Client {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.2.1 创建索引

```java
package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * @Classname ESTest_Index_Create
 * @Description 索引创建
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Index_Create {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("student");
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);

        // 响应状态
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引操作" + acknowledged);


        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.2.2 查看索引

```java
package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

/**
 * @Classname ESTest_Index_Search
 * @Description 索引查询
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Index_Search {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        // 创建索引
        GetIndexRequest request = new GetIndexRequest("student");
        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);

        System.out.println("response.getAliases() = " + response.getAliases());
        System.out.println("response.getMappings() = " + response.getMappings());
        System.out.println("response.getSettings() = " + response.getSettings());

        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.2.3 删除索引

```java
package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Classname ESTest_Index_Delete
 * @Description 索引删除
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Index_Delete {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        // 创建索引
        DeleteIndexRequest request = new DeleteIndexRequest("student");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
        // 关闭ES客户端
        esClient.close();
    }
}
```

## 6.3 文档操作

### 6.3.1 新增文档

```java
package com.zhuang.springbootelasticsearch.entity;

import lombok.Data;

/**
 * @Classname Student
 * @Description Student实体
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
@Data
public class Student {
    private String name;
    private String sex;
    private Integer age;
}
```

创建数据，添加到文档中

```java
package com.zhuang.springbootelasticsearch.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuang.springbootelasticsearch.entity.Student;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Insert
 * @Description 插入文档
 * @Date 2021/12/22 19:21
 * @Author by dell
 */
public class ESTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        IndexRequest resquest = new IndexRequest();
        resquest.index("student").id("1001");
        Student student = new Student();
        student.setName("张三");
        student.setSex("男");
        student.setAge(18);

        // 转为JSON
        ObjectMapper mapper = new ObjectMapper();
        String studentJson = mapper.writeValueAsString(student);
        resquest.source(studentJson, XContentType.JSON);

        IndexResponse response = esClient.index(resquest, RequestOptions.DEFAULT);
        System.out.println("response.getResult() = " + response.getResult());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.2 修改文档

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Update
 * @Description 更新文档
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Doc_Update {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("student").id("1001");
        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getResult());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.3 查询文档

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Get
 * @Description 查询文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Get {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //修改数据
        GetRequest request = new GetRequest();
        request.index("student").id("1001");

        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getSourceAsString());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.4 删除文档

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Delete
 * @Description 删除文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Delete {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        DeleteRequest request = new DeleteRequest();
        request.index("student").id("1001");

        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println("response.getResult() = " + response.toString());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.4 批量操作

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Insert_Batch
 * @Description 批量插入文档
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        BulkRequest resquest = new BulkRequest();
        // 批量添加
        resquest.add(new IndexRequest().index("student").id("1002").source(XContentType.JSON, "name", "lisi", "age", 15, "sex", "男"));
        resquest.add(new IndexRequest().index("student").id("1003").source(XContentType.JSON, "name", "wangwu", "age", 18, "sex", "女"));
        resquest.add(new IndexRequest().index("student").id("1004").source(XContentType.JSON, "name", "zhaoliu", "age", 20, "sex", "女"));
        resquest.add(new IndexRequest().index("student").id("1004").source(XContentType.JSON, "name", "tianqi", "age", 30, "sex", "女"));

        BulkResponse responses = esClient.bulk(resquest, RequestOptions.DEFAULT);
        System.out.println("responses.getTook() = " + responses.getTook());
        System.out.println("responses.getItems() = " + responses.getItems());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.5 批量删除

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Delete
 * @Description 批量删除文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Delete_Batch {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        BulkRequest resquest = new BulkRequest();
        // 批量添加
        resquest.add(new DeleteRequest().index("student").id("1002"));
        resquest.add(new DeleteRequest().index("student").id("1003"));
        resquest.add(new DeleteRequest().index("student").id("1004"));

        BulkResponse responses = esClient.bulk(resquest, RequestOptions.DEFAULT);
        System.out.println("responses.getTook() = " + responses.getTook());
        System.out.println("responses.getItems() = " + responses.getItems());
        // 关闭ES客户端
        esClient.close();
    }
}
```

### 6.3.6 高级查询

```java
package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;


import java.io.IOException;

/**
 * @Classname ESTest_Doc_Query
 * @Description 查询花样
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //全部查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 条件查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 分页查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // （当前页码-1）*每页显示数据条数
        builder.from(0);
        builder.size(2);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

       // 查询排序
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 过滤字段
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        String[] excludes = {"age"};
        String[] includes = {};
        builder.fetchSource(includes, excludes);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 组合查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age",30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age",18));

        builder.query(boolQueryBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

       // 范围查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(16);
        builder.query(rangeQuery);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/


        // 模糊查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.fuzzyQuery("name", "wangewu").fuzziness(Fuzziness.ONE));
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 高亮查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "lisi");
        builder.query(termQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font colr='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        builder.highlighter(highlightBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 聚合查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");

        builder.aggregation(aggregationBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }

        // 分组查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");

        builder.aggregation(aggregationBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }
        // 关闭ES客户端
        esClient.close();
    }
}
```

