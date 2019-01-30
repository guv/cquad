(defproject cquad "0.2.1"
  :description "Library for calculating improper integrals."
  :url "https://github.com/guv/cquad"
  :scm {:name "git"
        :url "https://github.com/guv/cquad"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.java.dev.jna/jna "5.2.0"]]

  :profiles {:dev {:dependencies [[org.apache.commons/commons-math3 "3.6.1"]]
                   :resource-paths ["resources/linux-x86-64" "resources/win32-x86-64"]}}

  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"])
