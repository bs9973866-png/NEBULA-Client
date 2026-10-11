# Publish NEBULA on GitHub and build the JAR

## Option A — upload this folder to a new repository

1. Sign in to GitHub and create a **new empty repository** named `NEBULA-Client`.
2. Do not add a README, `.gitignore`, or license during repository creation (these are already included here).
3. Unzip `NEBULA-Client-Source.zip` on your computer.
4. Open the new repository on GitHub and choose **Add file → Upload files**.
5. Upload the *contents* of the `nebula-client` folder, including the hidden `.github` directory. If your browser hides dot-folders, use Git locally instead (Option B).
6. Commit to `main`. Open the **Actions** tab and wait for **Build NEBULA Client** to finish.
7. Open the completed workflow run, scroll to **Artifacts**, and download `nebula-client-1.21.1-fabric`. The artifact contains the built JAR(s).

## Option B — recommended, using Git

Install Git, unzip the archive, open a terminal in the `nebula-client` directory, then run:

```bash
git init
git add .
git commit -m "Initial NEBULA Client source"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/NEBULA-Client.git
git push -u origin main
```

Replace `YOUR-USERNAME` with your GitHub username and create the empty repository first. The workflow runs automatically after the push. You do not need Gradle installed locally; GitHub Actions installs Gradle 8.10.2 and Java 21.

## Build locally

With Java 21 and Gradle 8.10.2 available:

```bash
gradle --no-daemon build
```

The output is in `build/libs/`. Use the JAR without the `-sources` suffix in the Minecraft `mods` folder, with Fabric Loader and Fabric API for Minecraft 1.21.1.

## Project status

This archive is a starter implementation. The build workflow verifies compilation and packages artifacts; it does not make placeholder module entries functional. Review the workflow result before using the JAR in-game.
