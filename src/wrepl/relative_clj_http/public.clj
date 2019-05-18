(ns wrepl.relative-clj-http.public
  (:require [clj-http.client :as http-client]
            [clojure.data.xml :as xml]
            [relative-clj-http.client :as client]))

(def config
  (atom client/default-config))

(defn pwd
  "Current base URL"
  []
  (client/pwd @config))

(defn old-pwd
  "Previous base URL"
  []
  (client/old-pwd @config))

(defmacro ^:private _cd
  [& args]
  `(-> (swap! config client/cd ~@args)
       (client/pwd)))

(defn cd
  "Change base url: back to home without args; relative move with one argument; textual replace old with new text with two arguments"
  ([]
   (_cd)) 
  ([path]
   (_cd path))
  ([old new]
   (_cd old new)))

(defn config!
  "Update the current config: show current config with no argument; update in path with value given last"
  ([]
   @config)
  ([& args] 
   (swap! config #(apply client/set-default % args))))

(defn request
  "Send a request with method and optional path and request"
  ([method]              (client/request @config method))
  ([method path]         (client/request @config method path))
  ([method path request] (client/request @config method path request)))

; TODO: make them a macro

(defn GET
  "GET request; same as (request :get ...)"
  ([]          (request :get))
  ([path]      (request :get path))
  ([path body] (request :get path body)))

(defn POST
  "POST request; same as (request :post ...)"
  ([]          (request :post))
  ([path]      (request :post path))
  ([path body] (request :post path body)))

(defn PUT
  "PUT request; same as (request :put ...)"
  ([]          (request :put))
  ([path]      (request :put path))
  ([path body] (request :put path body)))

(defn DELETE
  "DELETE request; same as (request :delete ...)"
  ([]          (request :delete))
  ([path]      (request :delete path))
  ([path body] (request :delete path body)))

(defn json
  "Create a JSON request from the passed boddy"
  [body]
  {:content-type :json 
   :body (http-client/json-encode body)})

(defn xml
  "Create a XML request from the passed boddy"
  [body]
  {:content-type :xml 
   :body (-> body xml/sexp-as-element xml/emit-str)})

(defn edn
  "Create a EDN request from the passed boddy"
  [body]
  {:content-type :application/edn
   :body (pr-str body)})
