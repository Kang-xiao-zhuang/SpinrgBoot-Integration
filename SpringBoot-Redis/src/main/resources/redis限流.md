---
title: 康小庄测试
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.20"

---

# 康小庄测试

Base URLs:

* <a href="">开发环境: </a>

* <a href="http://test-cn.your-api-server.com">测试环境: http://test-cn.your-api-server.com</a>

* <a href="http://prod-cn.your-api-server.com">正式环境: http://prod-cn.your-api-server.com</a>

# Authentication

# Default

## POST 用户列表

POST /localhost:8080/user/get

> Body Parameters

```json
{
  "id": 1,
  "name": "庄康"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# assistant

## POST 应用列表

POST /localhost:8080/assistant/app/list

> Body Parameters

```json
{
  "pageNum": 1,
  "pageSize": 5
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 成功

```json
{
  "code": 0,
  "msg": "成功",
  "data": {
    "total": 4,
    "list": [
      {
        "appId": 5,
        "appName": "线路老化",
        "createTime": "2024-01-01T16:00:00.000+00:00",
        "updateTime": "2024-01-01T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 6,
        "appName": "屏幕失真",
        "createTime": "2023-11-25T16:00:00.000+00:00",
        "updateTime": "2023-12-30T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 7,
        "appName": "设备损坏",
        "createTime": "2023-11-06T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 8,
        "appName": "文件丢失",
        "createTime": "2023-11-26T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      }
    ],
    "pageNum": 1,
    "pageSize": 5,
    "size": 4,
    "startRow": 1,
    "endRow": 4,
    "pages": 1,
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 应用参数详情

POST /localhost:8080/assistant/app/param

> Body Parameters

```json
{
  "appId": 1
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 成功

```json
{
  "code": 0,
  "msg": "成功",
  "data": {
    "total": 4,
    "list": [
      {
        "appId": 5,
        "appName": "线路老化",
        "createTime": "2024-01-01T16:00:00.000+00:00",
        "updateTime": "2024-01-01T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 6,
        "appName": "屏幕失真",
        "createTime": "2023-11-25T16:00:00.000+00:00",
        "updateTime": "2023-12-30T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 7,
        "appName": "设备损坏",
        "createTime": "2023-11-06T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 8,
        "appName": "文件丢失",
        "createTime": "2023-11-26T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      }
    ],
    "pageNum": 1,
    "pageSize": 5,
    "size": 4,
    "startRow": 1,
    "endRow": 4,
    "pages": 1,
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 应用运行

POST /localhost:8080/assistant/app/run

> Body Parameters

```json
{
  "appId": 1,
  "paramList": [
    {
      "paramName": "整数名称1",
      "defaultParam": "123",
      "paramType": 0
    },
    {
      "paramName": "字符名称1",
      "defaultParam": "apptest",
      "paramType": 1
    },
    {
      "paramName": "布尔名称1",
      "defaultParam": "false",
      "paramType": 2
    }
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 成功

```json
{
  "code": 0,
  "msg": "成功",
  "data": {
    "total": 4,
    "list": [
      {
        "appId": 5,
        "appName": "线路老化",
        "createTime": "2024-01-01T16:00:00.000+00:00",
        "updateTime": "2024-01-01T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 6,
        "appName": "屏幕失真",
        "createTime": "2023-11-25T16:00:00.000+00:00",
        "updateTime": "2023-12-30T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 7,
        "appName": "设备损坏",
        "createTime": "2023-11-06T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      },
      {
        "appId": 8,
        "appName": "文件丢失",
        "createTime": "2023-11-26T16:00:00.000+00:00",
        "updateTime": "2023-12-31T16:00:00.000+00:00",
        "userId": 2
      }
    ],
    "pageNum": 1,
    "pageSize": 5,
    "size": 4,
    "startRow": 1,
    "endRow": 4,
    "pages": 1,
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 工单列表

POST /localhost:8080/assistant/ticket/list

> Body Parameters

```json
{
  "pageNum": 1,
  "pageSize": 5
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 日志列表

POST /localhost:8080/assistant/log/list

> Body Parameters

```json
{
  "pageNum": 1,
  "pageSize": 5
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 任务概览

POST /localhost:8080/assistant/job/overview

> Body Parameters

```json
{}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 任务列表

POST /localhost:8080/assistant/job/list

> Body Parameters

```json
{
  "pageNum": 1,
  "pageSize": 5
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 任务和附件详情

POST /localhost:8080/assistant/job/detail

> Body Parameters

```json
{
  "jobId": 2
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 阅读任务

POST /localhost:8080/assistant/job/readall

> Body Parameters

```json
[
  1,
  2
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 阅读工单

POST /localhost:8080/assistant/ticket/readall

> Body Parameters

```json
[
  5
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 任务取消

POST /localhost:8080/assistant/job/cancel

> Body Parameters

```json
{
  "jobId": 1
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 任务录屏下载

GET /localhost:8080/assistant/job/video/download

> Body Parameters

```yaml
jobId: "1"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|
|» jobId|body|integer| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 任务附件下载

GET /localhost:8080/assistant/job/attach/download

> Body Parameters

```yaml
jobId: "1"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|userid|header|string| no |none|
|body|body|object| no |none|
|» jobId|body|integer| no |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# redis限流

## GET 固定时间窗口算法

GET /localhost:8888/limit/start

> Body Parameters

```yaml
times: "5"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» times|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "success": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» success|string|true|none||none|

## GET 滑动时间窗口算法

GET /localhost:8888/limit/startList

> Body Parameters

```yaml
times: "5"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» times|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "success": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» success|string|true|none||none|

## GET 漏桶算法

GET /localhost:8888/limit/startLoutong

> Body Parameters

```yaml
times: "5"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» times|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "success": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» success|string|true|none||none|

## GET 令牌桶算法

GET /localhost:8888/limit/startLingpaitong

> Body Parameters

```yaml
times: "5"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» times|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "success": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» success|string|true|none||none|

# Data Schema

