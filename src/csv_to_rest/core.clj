(ns csv-to-rest.core
  (:gen-class))

(require '[clojure.data.csv   :as csv]
         '[clojure.java.io    :as io]
         '[semantic-csv.core  :as sc :refer :all])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def atlanta "resources/Atlanta_Strategic_Community_Investment_2013.csv")

(defn blank?
  "Determines if a string is blank."
  [val]
  (or
    (nil? val)
    (empty? (clojure.string/trim val))))


(defn filter-empties
  "Omits map entries that have blank values."
  [aMap]
  ; not working
  (apply dissoc aMap (map key (filter (fn [entry] (blank? (val entry))) aMap))))


(defn csv-to-maps
  "Produces a seq of maps representing each r ow in the csv file."
  [filename]
  (with-open [in-file (io/reader filename)]
    (->> (csv/read-csv in-file)
         remove-comments
         mappify
         (map filter-empties)
         doall)))


(def atlMaps (csv-to-maps atlanta))


; create a map of sets from the values
(defn distinct-cols
  "Given a seq of maps, create sets of distinct values for each key and return a map with those sets."
  [maps]
  (reduce
    (fn [acc aMap]
      (into {}
        (map
          (fn [[key, val]] [key (conj (get acc key #{}) val)])
          aMap)))
    {}
    maps))

(def atlDistinct (distinct-cols atlMaps))

; transform reduced map of sets to a map of counts
(defn count-cols
  "Given a map of sets, create a map of size of the sets."
  [sets]
  (into {}
    (map (fn [[key val]] [key (count val)])
    sets)))


(def atlCounts (count-cols atlDistinct))


(defn sort-by-val
  "sort a map by the contents"
  [map]
  (into
    (sorted-map-by
      (fn [k1 k2]
        (compare
          (get map k1)
          (get map k2))))
    map))

(sort-by-val atlCounts)