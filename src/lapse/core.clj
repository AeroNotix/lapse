(ns lapse.core
  (:require [clojure.java.io :as io]
            [clojure.test.check.generators :as gen]
            [clojure.data.json :as json]))


(declare schema->generator)

(defn read-schema [name]
  (-> name
      io/resource
      slurp
      (json/read-str :key-fn keyword)
      :properties))


(def basic-type->generator
  {"string"  gen/string-ascii
   "integer" gen/int
   "boolean" gen/boolean
   "null"    (gen/return nil)})

(defn make-generator [mappings]
  (let [flap (flatten (seq mappings))]
    (apply gen/hash-map flap)))

(defn determine-gen [type params]
  (or (basic-type->generator type)
      (when (and (= type "array") (get-in params [:items :type]))
        (gen/vector (basic-type->generator (get-in params [:items :type]))))
      (when (get-in params [:items :$ref])
        (gen/vector (schema->generator (get-in params [:items :$ref]))))
      (when (:$ref params)
        (schema->generator (:$ref params))))))

(defn mixture [type params]
  (when (vector? type)
    (let [gens (map #(determine-gen % params) type)]
      (gen/one-of gens))))

(defn schema->generator [name & {:keys [overrides]}]
  (let [schema (read-schema name)]
    (make-generator
     (into {} (for [[k v] schema]
                (if (k overrides)
                  (k overrides)
                  [k (or (basic-type->generator (:type v))
                         (mixture (:type v) v)
                         (when (:$ref v)
                           (schema->generator (:$ref v))))]))))))
