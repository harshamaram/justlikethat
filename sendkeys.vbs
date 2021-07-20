' how to run?
' save the contents of this file to, say, "sendkeys.vbs"
' open powershell
' cscript sendkeys.vbs

set WshShell = WScript.CreateObject("WScript.Shell")
DO 
WScript.Sleep 60000 
WshShell.sendKeys "{SCROLLLOCK}" 
WScript.Sleep 500
WshShell.sendKeys "{SCROLLLOCK}" 
LOOP 
