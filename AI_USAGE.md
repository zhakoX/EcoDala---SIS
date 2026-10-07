# AI Usage

## AI tools used

I used ChatGPT as an AI assistant during the development of the EcoDala SIS3 project.

I mainly used it for:
- understanding Jetpack Compose concepts;
- reviewing the project structure;
- creating and refactoring reusable Compose components;
- implementing Navigation Compose;
- checking SIS3 requirements;
- debugging and improving UI code;
- adding Light and Dark mode previews;
- reviewing the final UI against the assignment requirements.

I made the final decisions about the application structure, UI, data, and functionality.

## Three useful prompts

### Prompt 1

> I am building an Android Jetpack Compose app for SIS3. I need at least 3 screens, a list screen with LazyColumn and 10 items, a LazyRow, reusable components, Navigation Compose, and Light/Dark previews. Help me structure the project into screens, components, data, model, navigation, and theme packages.

### Prompt 2

> Here is my current Jetpack Compose component. Add a Light Preview and a Dark Mode Preview, keep the existing logic, and give me the complete file so I can replace the current file.

### Prompt 3

> Review my current EcoDala project against the SIS3 requirements. Check Scaffold, TopAppBar, LazyColumn with 10+ items, LazyRow, navigation with an id argument, ellipsis, empty state, Image with contentDescription, custom color scheme, dark mode, reusable composables, previews, and remember with mutableStateOf.

## One case where AI was wrong or incomplete

One issue happened while implementing the custom EcoDala color scheme.

The first version of the theme used a green primary color, but Dynamic Color was still enabled. Some Material 3 colors such as surface container colors were therefore still appearing with a purple/gray tint in the application.

I noticed the problem by running the application and comparing the Light and Dark UI. The Quick Actions, achievement cards, and bottom navigation did not visually match the green EcoDala design.

I fixed the problem by creating a complete custom Light and Dark color scheme and disabling Dynamic Color. I also defined the surface container colors so that the Material 3 components use the EcoDala color system consistently.

## What I changed or wrote by hand

I manually reviewed the generated code and decided which parts were appropriate for the project.

I also manually:
- tested navigation between screens;
- checked the Light and Dark UI;
- verified the leaderboard data;
- checked the recycling point detail screen;
- tested the favorite interaction;
- reviewed the project structure;
- selected the application data and text;
- checked the final UI in Android Studio previews and the emulator.

The final application was tested and adjusted in Android Studio rather than being accepted without review.