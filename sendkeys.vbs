' how to run?
' save the contents of this file to, say, "sendkeys.vbs"
' open powershell
' cscript sendkeys.vbs

set WshShell = WScript.CreateObject("WScript.Shell")
DO 
Dim randNum, min, max
min = 90
max = 172
randNum = Int((max-min)*Rand + min) * 1000
WScript.Sleep randNum 
WshShell.sendKeys "{SCROLLLOCK}"
WshShell.sendKeys "{SCROLLLOCK}" 
LOOP 
