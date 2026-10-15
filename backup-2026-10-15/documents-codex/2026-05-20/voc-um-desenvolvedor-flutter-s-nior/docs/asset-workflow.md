# AF Health Lab Asset Workflow

Use the generated boards as source references, not as direct UI images.

## Where Things Live

- `assets/reference/source_sheets/`: original visual boards for reference.
- `assets/brand/svg/`: real logo assets used by Flutter.
- `assets/icons/svg/`: real icon assets used by Flutter.
- `assets/patterns/png/`: cropped raster textures, glows, ECG lines, and overlays.
- `assets/illustrations/png/`: cropped raster hero/dashboard illustrations.
- `assets/app_icon/png/`: launcher icon exports.

## How To Use In Flutter

Always reference assets through `AssetPaths`:

```dart
Image.asset(AssetPaths.runnerHeroPng, fit: BoxFit.cover)
SvgPicture.asset(AssetPaths.logoVertical)
```

For logos and icons, prefer SVG because it stays sharp on all screens. For photo-like or textured pieces, use PNG crops.

## Current Crops

- `AssetPaths.darkBasePng`
- `AssetPaths.hexBackgroundPng`
- `AssetPaths.greenGlowPng`
- `AssetPaths.dotMatrixPng`
- `AssetPaths.ecgWavePng`
- `AssetPaths.runnerHeroPng`
- `AssetPaths.dashboardStripPng`
- `AssetPaths.splashOverlayTopPng`
- `AssetPaths.splashOverlayBottomPng`
- `AssetPaths.loginOverlayLeftPng`
- `AssetPaths.loginOverlayRightPng`

## Practical Rule

Build the layout with Flutter widgets first: text, buttons, fields, cards, spacing, and navigation. Then layer assets behind or inside those widgets as decoration. Do not use a full mockup screenshot as the app screen, because it will not adapt to real devices.
