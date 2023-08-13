# Java Music Player Mini-Project

## Steps to configure the build path

### Method 1 (Directly edit the .classpath file):
1. Open the `.classpath` in any text editor
1. Replace the line `<classpathentry kind="src" path="src"/>` with the following lines:
```
<classpathentry excluding="assets/" kind="src" path="src"/>
<classpathentry kind="src" path="src/assets"/>
```
3. Above the line `<classpathentry kind="output" path="bin"/>`, add the following line 
`<classpathentry kind="lib" path="<path-to-your-project-folder>/src/libraries/jl1.0.1.jar"/>`

### Method 2 (GUI):
1. Open the project in Eclipse
1. In the Project Explorer, right click on the project, select **Build Path** and then **Configure Build Path**
1. Under the `Source` tab, select the downward arrow next to `<your-project-name>/src` folder 
1. Double click on the `Excluded` field
1. Under `Exclusion patterns`, select `Add` and then perform any of the following:
      1. Select `Browse` and double click on the `assets` folder, OR
      1. Type `assets/` in the input bar
1. Close the dialog box
1. Select `Add Folder` and add the `src/assets` folder
1. Under the `Libraries` tab, select `Classpath`, then select `Add External JARs`
1. Select the `jl1.0.1.jar` file in the `libraries` folder
1. Select `Apply and Close`