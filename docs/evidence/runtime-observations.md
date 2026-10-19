# Runtime observations (Pixel_7, Android API 35)

19 October 2026. Captured XML and screenshots are actual emulator output; emulator Logcat uses its own UTC clock and was not rewritten to local time.

- Registration with SIS4Student/student@ecodala.kz passed and opened Home directly.
- Search campus + Paper + favourite survived two real Activity recreations; actual Logcat is attached.
- Details opened Campus Paper Hub rather than a default first item.
- Explicit Loading error remained after Retry, recovered when demo error mode was disabled.
- does-not-exist showed the Empty state.
- Scrolling recorded 11 distinct locations (>=10). The fixture/unit test count is 12; scroll sampling skipped one title between frames.
- 5 kg Plastic produced Approved +175 pts. Home balance changed 845 → 1020; tree reached Level 10; student rank became #6 with 1020; current faculty changed 7890 → 8065.
- History gained a sixth submission (+175); its waste-only earned points total is 360, distinct from the 1020 wallet including historical milestone credits.
- Challenges progressed 3/5 → 4/5; Plastic Saver unlocked; achievements remained visible.
- Theme switched to dark; settings showed Russian and Kazakh labels. Force-stop/start retained the Kazakh language and 1020 points.
- These XML captures exposed pre-existing misleading labels: Home showed Level 11 at maximum level; Profile still displayed fixed 75/Level 5; leaderboard used a fixed EcoWarrior label. Source was corrected after the captures: UiState now computes capped milestones and score, and the ranking uses the actual demo display name. The final build/tests cover these corrections; pre-fix XML is kept as honest evidence, not presented as final expected text.

The complete visual UI was not compared pixel-by-pixel with the original app, and all possible device sizes and click targets were not exhaustively audited. The final verification report distinguishes source/unit evidence from runtime checks.

Final post-fix APK was also exercised: Home 1020/no Level 11, Profile score 100/actual identity, and actual name in leaderboard all passed assertions. The corrected map viewport no longer overlaps filters (recycling-map-final.png). See final-apk-smoke-check.txt and final-*.xml.
