# 注意事项

##  `emulateJSON = true`

增加配置，请求为一个普通的表单请求格式

## `Vue.http.options.root = '/root';` 

设置全局请求根访问路径 /root , 后续每个请求都会在请求路径加上根路径

并且后续请求必须是相对路径，才能使用启用根路径

## `Vue.http.options.emulateJSON = true`

If your web server can't handle requests encoded as `application/json`, you can enable the `emulateJSON` option. This will send the request as `application/x-www-form-urlencoded` MIME type, as if from an normal HTML form.
如果Web服务器无法处理编码为“application/json”的请求，则可以启用“emulatejson”选项。这将以“application/x-www-form-urlencoded”mime类型发送请求，就像从普通HTML表单发送一样。

## `Vue.http.options.emulateHTTP = true;`

如果您的Web服务器无法处理REST/HTTP请求，如“put”、“patch”和“delete”，则可以启用“emulateHTTP”选项。这将使用实际的http方法设置“x-http-method-override”头，并使用普通的“post”请求。

# Configuration

Set default values using the global configuration.

```js
Vue.http.options.root = '/root';
Vue.http.headers.common['Authorization'] = 'Basic YXBpOnBhc3N3b3Jk';
```

Set default values inside your Vue component options.

```js
new Vue({

  http: {
    root: '/root',
    headers: {
      Authorization: 'Basic YXBpOnBhc3N3b3Jk'
    }
  }

})
```

Note that for the root option to work, the path of the request must be relative. This will use this the root option: `Vue.http.get('someUrl')` while this will not: `Vue.http.get('/someUrl')`.

## Webpack/Browserify

Add `vue` and `vue-resource` to your `package.json`, then `npm install`, then add these lines in your code:

```js
var Vue = require('vue');
var VueResource = require('vue-resource');

Vue.use(VueResource);
```

## Legacy web servers

If your web server can't handle requests encoded as `application/json`, you can enable the `emulateJSON` option. This will send the request as `application/x-www-form-urlencoded` MIME type, as if from an normal HTML form.

```js
Vue.http.options.emulateJSON = true;
```

If your web server can't handle REST/HTTP requests like `PUT`, `PATCH` and `DELETE`, you can enable the `emulateHTTP` option. This will set the `X-HTTP-Method-Override` header with the actual HTTP method and use a normal `POST` request.

```js
Vue.http.options.emulateHTTP = true;
```

## Typescript Support

Typescript for vue-resource should work out of the box since the type definition files are included within the npm package.


# HTTP

The http service can be used globally `Vue.http` or in a Vue instance `this.$http`.

## Usage

A Vue instance provides the `this.$http` service which can send HTTP requests. A request method call returns a [Promise](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise) that resolves to the response. Also the Vue instance will be automatically bound to `this` in all function callbacks.

```js
{
  // GET /someUrl
  this.$http.get('/someUrl').then(response => {
    // success callback
  }, response => {
    // error callback
  });
}
```

## Methods

Shortcut methods are available for all request types. These methods work globally or in a Vue instance.

```js
// global Vue object
Vue.http.get('/someUrl', [config]).then(successCallback, errorCallback);
Vue.http.post('/someUrl', [body], [config]).then(successCallback, errorCallback);

// in a Vue instance
this.$http.get('/someUrl', [config]).then(successCallback, errorCallback);
this.$http.post('/someUrl', [body], [config]).then(successCallback, errorCallback);
```
List of shortcut methods:

* `get(url, [config])`
* `head(url, [config])`
* `delete(url, [config])`
* `jsonp(url, [config])`
* `post(url, [body], [config])`
* `put(url, [body], [config])`
* `patch(url, [body], [config])`

## Config

Parameter | Type | Description
--------- | ---- | -----------
url | `string` | URL to which the request is sent
body | `Object`, `FormData`, `string` | Data to be sent as the request body
headers | `Object` | Headers object to be sent as HTTP request headers
params | `Object` | Parameters object to be sent as URL parameters
method | `string` | HTTP method (e.g. GET, POST, ...)
responseType | `string` | Type of the response body (e.g. text, blob, json, ...)
timeout | `number` | Request timeout in milliseconds (`0` means no timeout)
credentials | `boolean` | Indicates whether or not cross-site Access-Control requests should be made using credentials
emulateHTTP | `boolean` | Send PUT, PATCH and DELETE requests with a HTTP POST and set the `X-HTTP-Method-Override` header
emulateJSON | `boolean` | Send request body as `application/x-www-form-urlencoded` content type
before | `function(request)` | Callback function to modify the request object before it is sent
uploadProgress | `function(event)` | Callback function to handle the [ProgressEvent](https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent) of uploads
downloadProgress | `function(event)` | Callback function to handle the [ProgressEvent](https://developer.mozilla.org/en-US/docs/Web/API/ProgressEvent) of downloads

## Response

A request resolves to a response object with the following properties and methods:

Property | Type | Description
-------- | ---- | -----------
url | `string` | Response URL origin
body | `Object`, `Blob`, `string` | Response body
headers | `Header` | Response Headers object
ok | `boolean` | HTTP status code between 200 and 299
status | `number` | HTTP status code of the response
statusText | `string` | HTTP status text of the response
**Method** | **Type** | **Description**
text() | `Promise` | Resolves the body as string
json() | `Promise` | Resolves the body as parsed JSON object
blob() | `Promise` | Resolves the body as Blob object

## Example

```js
{
  // POST /someUrl
  this.$http.post('/someUrl', {foo: 'bar'}).then(response => {

    // get status
    response.status;

    // get status text
    response.statusText;

    // get 'Expires' header
    response.headers.get('Expires');

    // get body data
    this.someData = response.body;

  }, response => {
    // error callback
  });
}
```

Send a get request with URL query parameters and a custom headers.

```js
{
  // GET /someUrl?foo=bar
  this.$http.get('/someUrl', {params: {foo: 'bar'}, headers: {'X-Custom': '...'}}).then(response => {
    // success callback
  }, response => {
    // error callback
  });
}
```

Fetch an image and use the blob() method to extract the image body content from the response.

```js
{
  // GET /image.jpg
  this.$http.get('/image.jpg', {responseType: 'blob'}).then(response => {

    // resolve to Blob
    return response.blob();

  }).then(blob => {
    // use image Blob
  });
}
```

## Interceptors

Interceptors can be defined globally and are used for pre- and postprocessing of a request. If a request is sent using `this.$http` or `this.$resource` the current Vue instance is available as `this` in a interceptor callback.

### Request processing
```js
Vue.http.interceptors.push(function(request) {

  // modify method
  request.method = 'POST';

  // modify headers
  request.headers.set('X-CSRF-TOKEN', 'TOKEN');
  request.headers.set('Authorization', 'Bearer TOKEN');

});
```

### Request and Response processing
```js
Vue.http.interceptors.push(function(request) {

  // modify request
  request.method = 'POST';

  // return response callback
  return function(response) {

    // modify response
    response.body = '...';

  };
});
```

### Return a Response and stop processing
```js
Vue.http.interceptors.push(function(request) {

  // modify request ...

  // stop and return response
  return request.respondWith(body, {
    status: 404,
    statusText: 'Not found'
  });
});
```

### Overriding default interceptors

All default interceptors callbacks can be overriden to change their behavior. All interceptors are exposed through the `Vue.http.interceptor` object with their names `before`, `method`, `jsonp`, `json`, `form`, `header` and `cors`.

```js
Vue.http.interceptor.before = function(request) {

  // override before interceptor

};
