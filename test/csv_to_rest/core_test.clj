(ns csv-to-rest.core-test
  (:require [clojure.test     :refer :all]
            [csv-to-rest.core :refer :all]))


(def atlanta "resources/Atlanta_Strategic_Community_Investment_2013.csv")
(def atlMaps (csv-to-maps atlanta))
(deftest test-csv-to-maps
  (testing "Load a csv file and convert it to maps"
    (is (= 246 (count atlMaps)))))


(def atlDistinct (distinct-cols atlMaps))
(deftest test-distinct-cols
  (testing "Check that distinct-cols creates a map of sets"
    (is (= #{"Yes" "No"} (:Sidewalks atlDistinct)))))


(def atlCounts (count-cols atlDistinct))
(deftest test-count-cols
  (testing "Check that count-cols creates a map of counts"
    (is (= 96 (:Neighborhood_Name atlCounts)))))


(def atlSorted (sort-by-val atlCounts))
(deftest test-sort-by-val
  (testing "Check that sort-by-val creates a sorted map of counts, ascending values"
    (is (= 1 (first (vals atlSorted))))
    (is (= 246 (last (vals atlSorted))))))
