# Recovery checkpoints

- Original folder had no Git repository (`git status` returned fatal).
- Before source edits, all original files were copied to `.sis4-backup/original-project.zip`: **2095 entries**, including ignored local configuration and generated output. Archive exists and its directory entries were inspected. A full extraction/rebuild of the archive was not run.
- SHA-256: `6c8c041775e547687a06cbb9a0af8137be2e1fff8cece1eb4c18388fc37857c6`.
- Original source checkpoint: `original-checkpoint`, commit `92d2f5d`.
- Tested refactor checkpoint: `refactor/sis4`, commit `81be0d2`.
- Final compatibility/runtime-correction checkpoint: `e8d0ceb`.
- JDK 21 configuration from the preceding task is included in the original checkpoint.
- No prior user changes were discarded. No reset/clean/force-push/history rewrite or remote push was used.
- To inspect an original file safely: `git show original-checkpoint:app/src/main/java/com/ecodala/feature/map/presentation/MapScreen.kt`.
- Restore the private ZIP into a separate folder if a complete original workspace is needed. Do not extract over the modified project.
- Keep the ZIP private: it includes local.properties. The backup directory is ignored by Git.
