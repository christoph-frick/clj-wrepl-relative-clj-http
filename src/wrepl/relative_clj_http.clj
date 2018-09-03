(ns wrepl.relative-clj-http
  (:require [integrant.core :as ig]
            [wrepl.relative-clj-http.public :refer [pwd]]))

(defmethod ig/init-key ::init
  [_ options]
  (fn []
    (require '[wrepl.relative-clj-http.public :refer :all])))

(defmethod ig/init-key ::prompt
  [_ options]
  (fn []
    (printf "%s => " (pwd))))
