(ns clj-lucene.test.document
  (:use [clj-lucene.document] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.document Document Field Field$Store Field$Index NumericField]))

(deftest test-field-name
  (testing "convert to field name"
    (is (= "title" (field-name "title")))
    (is (= "title" (field-name :title)))))

(deftest test-field
  (testing "construct a field from mapentry"
    (let [a (field :a 1 nil)
	  b (field :b "b" nil)]
      (is (instance? NumericField a))
      (is (= "a" (.name a)))
      (is (= 1 (.getNumericValue a)))
      (is (instance? Field b)))))

(deftest test-document
  (testing "Convert map to document"
    (let [d (document {:a 1 :b "b"})]
      (is (instance? Document d))
      (is (= 2 (count (.getFields d)))))))