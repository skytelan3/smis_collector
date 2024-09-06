# 배포자 확인 사항
1. target에서 jar 파일과 lib디렉터리를 복사하여 dist에 넣음
2. generated-resurces/properties/내용물을 dist/resources/properties 에 넣음
3. cmd 창을 열고, dist로 이동하여 java -jar Smis_Collector.jar 수행 해봄

# 사용자 확인 사항 (확인사항)
1. resources/properties/smis.properties 수정

## SMI-S Config
smis.server.addr=https://10.10.10.246:5989 # 스토리지 SMI-S 접속 주소
smis.server.id=administrator # 스토리지 SMI-S 접속 ID
smis.server.password=123qweASD! # 스토리지 SMI-S 접속 PW
smis.server.namespace=root/hpq # 스토리지 SMI-S의 namespace
smis.server.vendorkey=hp_msa2050 # 스토리지 SMI-S의 벤더 명 (아무렇게나 해도 상관 없긴함)

## Instance Config (true : Save instances as files, false : Print instances to console only)
isFileWrite=true # 인스턴스 파일 수집 플래그
default.save.path = ./src/main/resources/instances/ # 파일 저장 경로, 저장이 잘 안될 경우 파일 경로 수정이 필요 (절대경로)

2. 실행
cmd 창 혹은 shell에서, Watchall_Storage_Monitoring.jar.remove가 있는 디렉터리로 이동
Watchall_Storage_Monitoring.jar.remove 파일을 Watchall_Storage_Monitoring.jar로 변경
java -jar Watchall_Storage_Monitoring.jar 실행
