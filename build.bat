del android\SamAds\build\libSamAds.jar

del SamAds.ane

xcopy android\SamAds\bin\classes android\SamAds\bin /S /Y /R

rd android\SamAds\bin\classes /S /Q

"c:\Program Files (x86)\Java\jdk1.7.0_51\bin\jar.exe" cvf android/SamAds/build/libSamAds.jar -C android/SamAds/bin .

SET PLATFORM_ANDROID= -platform Android-ARM -C android\SamAds\build\ .
SET PLATFORM_DEFAULT= -platform default -C default\ .

"c:\_dev\airsdk3.9\bin\adt.bat" -package -target ane SamAds.ane air\extension.xml -swc air/SamAds/bin/SamAds.swc %PLATFORM_ANDROID% %PLATFORM_DEFAULT%