# IntelliJ settings
* src/main/groovy
* src/main/java
* src/main/jython

... have to be marked as source directories. Then add the python interpreter to the module:
```
Project Structure > Module > Select intellij-ij-scripting module > + > Python 
```

# Usage
Run the `LaunchImageJ`. This will open an ImageJ instance and 
prompt you to open some scripts. 

Run the scripts from the _IJ Script Editor_, to have the full environment (script parameters, etc.)
Use _IntelliJ_ to edit the scripts, to profit from code completion. Once you switch back to the IJ script editor, 
you will be prompted to reload the script. 