(ns hedgehog-interview.handler-test
  (:require
    [clojure.test :refer :all]
    [ring.mock.request :refer :all]
    [hedgehog-interview.handler :refer :all]
    [hedgehog-interview.middleware.formats :as formats]
    [muuntaja.core :as m]
    [mount.core :as mount]))

(defn parse-json [body]
  (m/decode formats/instance "application/json" body))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'hedgehog-interview.config/env
                 #'hedgehog-interview.handler/app-routes)
    (f)))

(deftest test-app
  (testing "main route"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
