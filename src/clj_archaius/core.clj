(ns clj-archaius.core
  (:import [com.netflix.config
            DynamicPropertyFactory])
  (:require [clojure.string :as cs]))


(defonce ^:private types {'int 0
                          'string nil
                          'boolean false
                          'double 0
                          'float 0
                          'long 0})

(defmacro define-env [name type method]
  `(do
     (defn ~name
       "Get current typed value of the property."
       ([~'k]
          (~name ~'k (types ~type)))
       ([~'k ~'default]
          (..
           DynamicPropertyFactory
           (getInstance)
           (~method (name ~'k) ~'default)
           (get))))
     (defn ~(symbol (str "on-" name))
       "Add the callback to be triggered when the value of
        the property is changed."
       [~'k ~'cb]
       (..
        DynamicPropertyFactory
        (getInstance)
        (~method (name ~'k) (types ~type))
        (addCallback ~'cb)))))

;;define env function
(define-env env 'string getStringProperty)

;;define concrete types
(defmacro define-types-env []
  `(do
     ~@(map
        (fn [type]
          (let [name (symbol (str (str type) "-env"))
                method (symbol (str "get" (cs/capitalize (str type)) "Property"))]
            `(define-env ~name (quote ~type) ~method)))
        (keys types))))

(define-types-env)
