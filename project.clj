(defproject net.ofnir/wrepl.relative-clj-http "0.1.2-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [integrant "0.8.0"]
                 [clj-http/clj-http "3.12.3"]
                 [net.ofnir/relative-clj-http "0.1.4"]
                 [net.ofnir/clj-http-request "0.1.1"]]
  :deploy-repositories {"releases" {:url "https://clojars.org/repo/" :creds :gpg}})
