(ns eastwood
  (:require
   [eastwood.lint :as e]))

(defn- >errf
  "Like printf but prints to STDERR"
  [fmt & args]
  (.println *err* (apply format (apply conj [fmt] args))))

(defn lint
  "TODO."
  [config]
  ;; TODO: merge config with default opts
  (let [opts {:exclude-namespaces #{}
              :source-paths #{}
              :test-paths #{}
              :linters #{}
              :exclude-linteres #{}
              :add-linters #{}
              :builtin-config-files #{}
              :config-files #{}}]
    (>errf "[cc-eastwood] analyzing")))
