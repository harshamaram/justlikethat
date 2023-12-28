:: C:\my-space\tools\ImageMagick-7.1.1-11-portable-Q16-x64\convert.exe 
:: "C:\Users\harsh\OneDrive\Pictures\2023-06 Sixtyth Birthday\DSC00464.JPG" -quality 80 
:: "C:\Users\harsh\OneDrive\Pictures\2023-06 Sixtyth Birthday (resized)\DSC00464.JPG"


@echo off
setlocal enabledelayedexpansion

set sourceFolder=C:\Users\harsh\OneDrive\Pictures\pictures folder
set targetFolder=C:\Users\harsh\OneDrive\Pictures\pictures folder (resize)
set fileName=""
set commandPath=C:\my-space\tools\ImageMagick-7.1.1-11-portable-Q16-x64\convert.exe

cd "%sourceFolder%"
echo !sourceFolder!

:: set runCmd=%commandPath% "%sourceFolder%" -quality 80 "%targetFolder%"
:: echo %runCmd%
for /f %%i in ('dir "!sourceFolder!\" /b') do ( 
	echo ---
	set runCmd=%commandPath% "%sourceFolder% %i%" -quality 80 "%targetFolder% %%i"
	echo %runCmd%
)
cd ..

