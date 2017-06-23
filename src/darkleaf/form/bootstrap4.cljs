(ns darkleaf.form.bootstrap4
  (:require
   [clojure.string :as string]
   [darkleaf.form.common :as common]
   [darkleaf.form.context :as ctx]))

(defn- class-names [& names]
  (->> names
       (remove nil?)
       (string/join " ")))

(defn- add-class [classes class]
  (if (string/blank? classes)
    class
    (str classes " " class)))

(defn errors [ctx]
  (let [errors (ctx/get-own-errors ctx)]
    [:div
     (for [error errors]
       ^{:key error}
       [:div.form-control-feedback
        (common/error-text ctx error)])]))

(defn label [ctx]
  [:label.form-control-label (common/label-text ctx)])

(defn top-classes [ctx & classes]
  (let [errors (ctx/get-own-errors ctx)
        has-errors? (not-empty errors)]
    (apply class-names
           (if has-errors? "has-danger")
           classes)))

(defn text [top-ctx id & {:as opts}]
  (let [ctx (ctx/nested top-ctx id)
        input-opts (-> {:type :text}
                       (merge opts)
                       (update :class add-class "form-control"))]
    [:div {:class (top-classes ctx "form-group")}
     [label ctx]
     [common/input ctx input-opts]
     [errors ctx]]))

(defn textarea [top-ctx id & {:as opts}]
  (let [ctx (ctx/nested top-ctx id)
        input-opts (update opts :class add-class "form-control")]
    [:div {:class (top-classes ctx "form-group")}
     [label ctx]
     [common/textarea ctx input-opts]
     [errors ctx]]))

(defn select [top-ctx id & {:as opts}]
  (let [ctx (ctx/nested top-ctx id)
        input-opts (update opts :class add-class "form-control")]
    [:div {:class (top-classes ctx "form-group")}
     [label ctx]
     [common/select ctx input-opts]
     [errors ctx]]))

(defn multi-select [top-ctx id & {:as opts}]
  (let [ctx (ctx/nested top-ctx id)
        input-opts (update opts :class add-class "form-control")]
    [:div {:class (top-classes ctx "form-group")}
     [label ctx]
     [common/multi-select ctx input-opts]
     [errors ctx]]))

(defn checkbox [top-ctx id & {:as opts}]
  (let [ctx (ctx/nested top-ctx id)
        input-opts (update opts :class add-class "custom-control-input")]
    [:div {:class (top-classes ctx "form-check")}
     [:label.custom-control.custom-checkbox
      [common/checkbox ctx input-opts]
      [:span.custom-control-indicator]
      [:span.custom-control-description (common/label-text ctx)]]
     (errors ctx)]))


;; TODO: radio, checkbox collection