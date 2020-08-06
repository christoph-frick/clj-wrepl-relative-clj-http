# clj-wrepl-relative-clj-http

Plugin for [`wrepl`](https://github.com/christoph-frick/clj-wrepl), that
wraps
[`relative-clj-http`](https://github.com/christoph-frick/relative-clj-http)
to allow interactive exploration of web services like REST-APIs.

This holds an atom with the per-repl configuration for
`relative-clj-http` and wraps all functions so the configuration in the
atom is used instead.

See
[`wrepl/doc/rest-repl.md`](https://github.com/christoph-frick/clj-wrepl/blob/master/doc/rest-repl.md)
for a full example of the setup.

## Infused functions

The following functions are available in the REPL once the init of this
plugin ran:

- `@config`: a local atom, that holds the current configuration
- `(config!)`: same as `@config`
- `(config! & args)`: Calls `relative-clj-http.client/set-default` on
  the current `@config`. `args` but the last element is used as a path
  into the configuration and the last element is the value to set.  E.g.
  `(config! :headers "Authorization" (str "Bearer " (:access_token body))`
- `GET`, `POST`, `PUT`, `DELETE`: Wrapper for `(request :get ...)`, ...
- Everything from
  [`relative-clj-http.client`](https://cljdoc.org/d/net.ofnir/relative-clj-http/CURRENT/api/relative-clj-http.client),
  except, that the first argument `config` is not used, but instead the
  local `@config` is used
- Everything from
  [`net.ofnir.clj-http-request`](https://cljdoc.org/d/net.ofnir/clj-http-request/0.1.0/api/net.ofnir.clj-http-request)



## Entry points

- `:wrepl.relative-clj-http/init` (no options): Must be called from
  `:wrepl/init` to setup the global state configuration and `use`es all
  it's method into the REPL
- `:wrepl.relative-clj-http/prompt` (no options): Can be wired to
  `:wrepl/prompt`; shows the current base URL of the configuration as
  prompt

## Example usage

```
User config from file:///$HOME/.wrepl/rest-repl.edn
http://localhost:8080 => (cd "https://httpbin.org")
"https://httpbin.org"
https://httpbin.org => (GET "/status/200")
{:body "",
 :cached nil,
 :chunked? false,
 :content-type :text/html,
 :content-type-params {:charset "utf-8"},
 :headers {"Access-Control-Allow-Credentials" "true",
           "Access-Control-Allow-Origin" "*",
           "Connection" "close",
           "Content-Length" "0",
           "Content-Type" "text/html; charset=utf-8",
           "Date" "Thu, 06 Aug 2020 08:53:26 GMT",
           "Server" "gunicorn/19.9.0"},
 :http-client #<org.apache.http.impl.client.InternalHttpClient@d61ac34>,
 :length 0,
 :orig-content-encoding nil,
 :protocol-version {:major 1, :minor 1, :name "HTTP"},
 :reason-phrase "OK",
 :repeatable? false,
 :request-time 646,
 :status 200,
 :streaming? false,
 :trace-redirects []}
https://httpbin.org => 
```
