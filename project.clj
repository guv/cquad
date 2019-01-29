(defproject cquad "0.1.0"
  :description "Library for calculating improper integrals."
  :url "http://github.com/guv/cquad"
  :license {:name "GNU Lesser General Public License 3"
            :url "https://www.gnu.org/licenses/lgpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.java.dev.jna/jna "5.2.0"]
                 [net.sourceforge.jdistlib/jdistlib "0.4.5"]]

  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"])
