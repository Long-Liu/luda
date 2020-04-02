#!/bin/bash
build_img() {
  env=$1
  cd .. &&
    mvn clean &&
    mvn compile &&
    mvn package -DskipTests &&
    java -jar target/luda.jar --spring.profiles.active=${env}
}
echo "开始构建Java项目${1}模式"
ENV=$1
case ${ENV} in
"dev")
  build_img dev
  ;;
"test")
  build_img test
  ;;
"prod")
  build_img prod
  ;;
*)
  build_img dev
  ;;
esac
echo "构建完成！"
