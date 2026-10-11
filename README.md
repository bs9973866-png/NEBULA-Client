# NEBULA Client — Fabric 1.21.1

A client-side starter project for a custom black/red Minecraft interface. The ClickGUI uses a bundled TTF font provider and custom panel styling rather than the pixel Minecraft font for its UI text.

## Features in this source build
- Fabric client mod targeting Minecraft 1.21.1 / Java 21.
- Right Shift opens the ClickGUI; Right Ctrl opens the HUD editor view.
- Searchable categories and a registry of 70+ module entries.
- Minimal custom watermark/FPS/coordinate HUD, controlled by module toggles.
- Dark UI with red accents, clipped module list, scrolling and draggable GUI window.
- Gradle settings for Fabric Loom, plus conservative JVM settings.

## Important status notes
This is an initial source project, not a claim that every listed module is implemented. Some registry entries are placeholders for future work (e.g. Discord presence, trajectory preview, profile switching). Hit Sounds and Swing Animation are currently module entries only and need their gameplay hooks/animation implementation before they do anything. The HUD editor screen is an initial UI scaffold, not yet a full persisted drag-and-drop editor. No Minecraft shader/effect pack is used.

The environment used to prepare this archive had Java 21 but no Gradle installation and no network/DNS access to Maven, so I could not resolve Fabric dependencies or build/test a real JAR here. Build on a machine with internet access and Gradle 8.10+ installed:

```bash
gradle build
```

The output JAR will be under `build/libs/`. Then copy it to `.minecraft/mods/` alongside Fabric API for Minecraft 1.21.1. If the selected Loom version requires a newer Gradle release, use the version specified by Fabric Loom's release notes.

## Controls
- **Right Shift** — open ClickGUI
- **Right Ctrl** — open HUD editor view
- Click a module card to toggle it; use search and category navigation.

## Module registry
The registry intentionally contains more than 50 categorized entries. Entries with no implementation are marked as future-facing names/descriptions in the source; they should not be assumed functional until implemented and tested.

## Build on GitHub (no local Gradle required)

This repository includes `.github/workflows/build.yml`. Push the project to GitHub, open **Actions**, and download the `nebula-client-1.21.1-fabric` artifact from a successful workflow run. Step-by-step upload instructions are in [`GITHUB_SETUP.md`](GITHUB_SETUP.md).
