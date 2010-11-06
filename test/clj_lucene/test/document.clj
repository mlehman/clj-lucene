(ns clj-lucene.test.document
  (:use [clj-lucene.document] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.document Document Field Field$Store Field$Index]))
