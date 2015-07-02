(ns clj-archaius.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clj-archaius.core :refer :all]))

(deftest test-env
  (testing "xxx-env"
    (is (= (int-env :a) 1))
    (is (= (env :a) "1"))
    (is (= (int-env :b) 2))
    (is (= (int-env :unknown) 0))
    (is (= (double-env :c) 3.3))
    (is (= (double-env :unknown) 0.0))
    (is (= (env :d) "hello"))
    (is (= (string-env :d) "hello"))
    (is (= (env :a.b.c) "world"))
    (is (= (string-env :a.b.c) "world"))
    (is (= (env :unknown) nil))
    (is (boolean-env :boolean.test))
    (is (not (boolean-env :boolean-test)))))

(defn mv-file [x y]
  (io/copy
   (io/file (io/resource x))
   (io/file (io/resource y))))

(deftest test-on-env
  (testing "on-int-env"
    (let [c (atom {})]
      (try
        (is (= (int-env :a) 1))
        (is (not (boolean-env :boolean-test)))
        (on-int-env :a #(swap! c assoc :a (int-env :a)))
        (on-boolean-env :boolean-test #(swap! c assoc :boolean-test (boolean-env :boolean-test)))
        (mv-file "test_config.properties"
                 "config.properties")
        (while (empty? @c)
          (Thread/sleep 100))
        (is (boolean-env :boolean-test))
        (is (@c :boolean-test))
        (is (= 100 (int-env :a)))
        (is (= 100 (@c :a)))
        (finally
          (mv-file "normal_config.properties"
                   "config.properties")
          (Thread/sleep 65000))))))
