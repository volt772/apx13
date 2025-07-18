#!/bin/bash

NC='\033[0m'
RED='\033[0;31m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
ONRED='\033[41m'
ONPURPLE='\033[45m'

echo '
 ______        _ _     _
(____  \      (_) |   | |
 ____)  )_   _ _| | _ | | ____  ____
|  __  (| | | | | |/ || |/ _  )/ ___)
| |__)  ) |_| | | ( (_| ( (/ /| |
|______/ \____|_|_|\____|\____)_|
-----------------------------------------
'
read -p "Enter apx999 TAG: " BTAG
BASE_DIR="/Users/allen/work/sources/apx/apx_projects/Android-apx999"
RELEASE_BUILD_DIR="$BASE_DIR/release_apx999_$BTAG""_bundle"

#: 기존 빌드 디렉토리 유무 검사
if [ -d "$RELEASE_BUILD_DIR" ]; then
	echo "----------------------------------------------------------------------------------\n"
	read -p "▶ 이미 존재함, 디렉토리를 제거한 뒤 다시 빌드하시겠습니까? [Yy / Nn] : " yn
	case $yn in
		[Yy]* ) rm -rf $RELEASE_BUILD_DIR break;;
		[Nn]* )
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× 사용자 취소\n${NC}"
			echo "----------------------------------------------------------------------------------"
			exit;;
		* ) echo
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× 입력범위 오류, 취소\n${NC}"
			echo "----------------------------------------------------------------------------------"
	esac
fi

#: TAG명 EXPORT
export BTAG

#: 빌드경로 이동 및 실행
cd $BASE_DIR
#sh ./buildReleaseAppBundleLocal.sh


#: BELOW IS BUILD PROCESS
echo "\n----------------------------------------------------------------------------------\n"
echo "${RED}빌드시작 \n${NC}"
echo "----------------------------------------------------------------------------------"

#: KeyStore 디렉토리 검사
KEY_STORE_DIR="./apx_keystore";

if [ ! -d "$KEY_STORE_DIR" ]; then
	echo "\n----------------------------------------------------------------------------------\n"
	echo "${RED}× Keystore 디렉토리 없음\n${NC}"
	echo "----------------------------------------------------------------------------------"
	exit
fi

#: BUNDLETOOL_FILE 검사
if [ ! -f "$BUNDLETOOL_FILE" ]; then
	echo "\n----------------------------------------------------------------------------------\n"
	echo "${RED}× Android BundleTool 없음\n${NC}"
	echo "----------------------------------------------------------------------------------"
	exit
fi

#: 시작
#read -p "Enter Git TAG: " BTAG

BUILD_DIR="./tmp-apx999-bundle-$BTAG"

#: 디렉토리 복사
mkdir -p $BUILD_DIR
cp -rf ./* $BUILD_DIR
cd $BUILD_DIR

#: BUILD 
read -p "Enter KeyStore Password : " KSPW
read -p "Enter Key Password : " KPW

export "apx999_KEYSTORE_PW="$KSPW
export "apx999_KEY_PW="$KPW

./gradlew clean :app:bundleRelease

#: Copy APK, Mapping File
OUTPUT_DIR="../release_apx999_$BTAG""_bundle"

mkdir -p $OUTPUT_DIR
cp -rf ./app/build/outputs/bundle/release/* $OUTPUT_DIR
cp -rf ./app/build/outputs/mapping/release/* $OUTPUT_DIR

# missing_rules.txt 복사 (존재할 경우만)
if [ -f "./app/build/outputs/mapping/release/missing_rules.txt" ]; then
  cp ./app/build/outputs/mapping/release/missing_rules.txt $OUTPUT_DIR
fi

#: Universal APKs 생성
java -jar "$BUNDLETOOL_FILE" build-apks --bundle=./app/build/outputs/bundle/release/app-release.aab --output=$OUTPUT_DIR/apx999-release-"$BTAG"-universal.apks --ks=../apx_keystore/apx999_keystore.jks --ks-pass=pass:$KSPW --ks-key-alias=apx999_alias --key-pass=pass:$KPW --mode=universal

#: Move to Output Directory
mv $OUTPUT_DIR/app-release.aab $OUTPUT_DIR/apx999-release-"$BTAG".aab

#: Delete Temporary build DIR
cd ..
rm -rf $BUILD_DIR

sync

#: end
echo "-------------------------------------------"
echo "App Bundle/APKs Result => $OUTPUT_DIR"
echo "-------------------------------------------"


#: Signing Process (hnjeong Custom)
signingVersion=`echo $OUTPUT_DIR | grep -Eo '[+-]?[0-9]+([.][0-9]+)+([.][0-9][a-z]+)?'`
cd "/Users/allen/work/sources/apx/apx_projects/Android-apx999/release_apx999_$signingVersion""_bundle"
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 -keystore ../apx_keystore/apx999_keystore.jks ./apx999-release-$signingVersion.aab apx999_alias
