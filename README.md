# Tezkor Akfa

**A Kotlin Android client for door and window order measurement, field documentation, and supplier purchasing.**

Tezkor Akfa explores the mobile side of a manufacturing workflow: a measurer opens an order, gathers customer and location information, selects products and quantities, and prepares drawings and PDF material. Supplier screens cover purchase orders, received products, history, and money records. Sign-in also routes installation and service roles to their own screen groups, although those integrations are incomplete in this snapshot.

The project combines a business API client with device features such as the camera, maps, signatures, local document storage, and PDF generation. The repository contains the Android application; the backend is external.

## Workflows and implementation status

| Area | What is present |
| --- | --- |
| Sign-in and routing | A login request, saved user/role state, and routing to measurer, supplier, installation, or service activities. |
| Measurement | Order lists and confirmation screens; customer details, address/location selection, photo capture, product-type and quantity selection. |
| Field documents | Drawing records, a signature screen, Room-backed PDF metadata, PDF creation, and multipart order-update/upload requests. |
| Supplier work | API contracts and screens for order lists/details, purchase creation, received products, purchase history, and money records. |
| Installation and service | Activities, navigation, and screen scaffolding. `MontageService` and `Servicer` are empty interfaces; the service-home list wiring is commented out. These are not complete live integrations. |
| Custom drawing UI | A separate window/door designer package with custom layouts, frames, areas, and dimension views. Parts remain experimental; the designer fragment itself retains template code. |

## A measurement flow through the source

1. **Sign in.** [LoginActivity](app/src/main/java/uz/algorithmgateway/tezkorakfa/presenter/ui/login/LoginActivity.kt) reads the returned role and opens the corresponding activity.
2. **Open an order.** The [measurer screens](app/src/main/java/uz/algorithmgateway/tezkorakfa/presenter/measurer/ui) and [navigation graph](app/src/main/res/navigation/navigation_measurer.xml) connect order lists to acceptance and confirmation.
3. **Collect field details.** [AcceptOrderScreen](app/src/main/java/uz/algorithmgateway/tezkorakfa/presenter/measurer/ui/accept_order/AcceptOrderScreen.kt) edits customer information, captures an image, uses location information, and constructs multipart update data.
4. **Prepare product and drawing data.** Product type/count screens and the local ViewModel maintain drawing information for the order.
5. **Create the document.** [SavePdfFragment](app/src/main/java/uz/algorithmgateway/tezkorakfa/presenter/measurer/ui/drawings/save_pdf/SavePdfFragment.kt) renders the drawing list to a PDF, records file metadata, and sends the document with product counts through the order API.

These steps describe code paths in the repository. Successful completion requires a compatible backend, an appropriate account, and working device permissions.

## Architecture and source map

The application is a single `:app` Gradle module with XML layouts, ViewBinding, Android Navigation, ViewModels, coroutine/Flow state, Retrofit/OkHttp, Dagger 2, and Room. Firebase Analytics and Crashlytics are configured in the build; Google Maps/location services, image libraries, and a signature-pad component support field capture.

```text
app/src/main/java/uz/algorithmgateway/tezkorakfa/
├── base/                 Application and dependency-component initialization
├── presenter/
│   ├── ui/               Sign-in, splash, and shared UI state
│   ├── measurer/         Measurement, product selection, drawings, and PDFs
│   ├── supplier/         Purchase, received-product, history, and money screens
│   ├── montage/          Installation screen group
│   ├── servis/           Service screen group
│   └── windowdoordisegner/ Custom window/door drawing experiments
├── data/
│   ├── retrofit/         API services, models, and network repositories
│   ├── local/            Room database, DAOs, and document metadata
│   └── models/           Shared request/response and UI data
└── di/                   Dagger component and provider modules
```

Useful starting points:

| File or directory | Why read it |
| --- | --- |
| [ApiService](app/src/main/java/uz/algorithmgateway/tezkorakfa/data/retrofit/api/ApiService.kt) | Login, order/catalogue loading, order acceptance/confirmation, updates, and downloads. |
| [SupplierService](app/src/main/java/uz/algorithmgateway/tezkorakfa/data/retrofit/api/SupplierService.kt) | The supplier purchase and money-record API surface. |
| [NetworkModule](app/src/main/java/uz/algorithmgateway/tezkorakfa/di/module/NetworkModule.kt) | Retrofit construction, HTTP interceptors, service provision, and request authorization setup. |
| [Local data](app/src/main/java/uz/algorithmgateway/tezkorakfa/data/local) | Drawing/PDF records, DAO access, and Room storage. |
| [Navigation resources](app/src/main/res/navigation) | Separate navigation graphs for each operational role. |
| [Window/door designer](app/src/main/java/uz/algorithmgateway/tezkorakfa/presenter/windowdoordisegner) | Custom drawing and dimension-view experiments. |

## Local development

### 1. Open the project

```sh
git clone https://github.com/JahongirmirzoDv/TezkorAkfa.git
cd TezkorAkfa
```

Open the root directory in Android Studio. The checked-in build declares:

| Setting | Value |
| --- | --- |
| Application ID | `uz.algorithmgateway.tezkorakfa` |
| Minimum Android API | 23 |
| Compile / target API | 31 / 31 |
| Android Gradle Plugin | 7.1.3 |
| Gradle wrapper | 7.2 |
| Kotlin Gradle plugin | 1.6.21 |
| Java / Kotlin bytecode target | 1.8 |

Use a toolchain compatible with this historical Android configuration. Older dependency repositories and libraries may require maintenance during Gradle sync.

### 2. Supply development service configuration

- Set the `BASE_URL` build fields in [app/build.gradle](app/build.gradle) for your development backend. Debug and release currently declare the same service URL.
- Review [NetworkModule](app/src/main/java/uz/algorithmgateway/tezkorakfa/di/module/NetworkModule.kt) before connecting: its interceptor adds a fixed authorization value. Replace that setup with credentials appropriate to the development environment; do not assume login alone configures request authorization.
- Provide Firebase configuration for the application package and configure your own Google Maps credentials in the relevant manifest/resources.
- Use test accounts with the required role and matching backend data. A server implementation, seed database, and demo login are not included.

### 3. Build and exercise on a device

```sh
sh gradlew :app:assembleDebug
```

This is the build entry point, not a recorded successful build. Use an emulator or physical device with the camera/location capabilities needed for the screens you explore. PDF handling uses shared external storage and includes Android 11+ all-files-access handling, so document behavior needs verification on the target Android version.

## Current boundaries

- Measurement and supplier paths have concrete API implementations; installation and service sections are less complete.
- The custom window/door designer contains working drawing classes alongside template and experimental code. A complete CAD or manufacturing-calculation system is not established by this repository.
- Camera, location, signatures, PDF generation/upload, and storage permission behavior require device testing. The minimum SDK declaration does not demonstrate that every document path works across all supported Android versions.
- The checked-in tests are the standard example unit and instrumentation files; they do not demonstrate coverage of order or purchasing workflows.
- Backend availability, authenticated end-to-end flows, and current Android release compatibility have not been verified by this documentation review.

## Existing screenshots

These repository screenshots show an earlier captured interface state.

![Tezkor Akfa application screen 1](https://github.com/JahongirmirzoDv/TezkorAkfa/blob/master/app/Screenshot%202024-08-27%20at%204.27.00%E2%80%AFAM.png?raw=true)

![Tezkor Akfa application screen 2](https://github.com/JahongirmirzoDv/TezkorAkfa/blob/master/app/Screenshot%202024-08-27%20at%204.27.11%E2%80%AFAM.png?raw=true)

![Tezkor Akfa application screen 3](https://github.com/JahongirmirzoDv/TezkorAkfa/blob/master/app/Screenshot%202024-08-27%20at%204.27.26%E2%80%AFAM.png?raw=true)

![Tezkor Akfa application screen 4](https://github.com/JahongirmirzoDv/TezkorAkfa/blob/master/app/Screenshot%202024-08-27%20at%204.27.43%E2%80%AFAM.png?raw=true)

![Tezkor Akfa application screen 5](https://github.com/JahongirmirzoDv/TezkorAkfa/blob/master/app/Screenshot%202024-08-27%20at%204.28.13%E2%80%AFAM.png?raw=true)
