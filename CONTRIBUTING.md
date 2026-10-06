# Contributing to HPView

Thanks for your interest in HPView. Bug reports, translations and pull requests are welcome.

## Reporting bugs and requesting features

- Use the issue templates on the [Issues](https://github.com/spa77k/mc-hpview/issues/new/choose) page.
- For bugs, include your Paper version, Java version, whether the player joined through Geyser, and steps to reproduce.
- For security problems, do not open a public issue. See [SECURITY.md](SECURITY.md).

## Pull requests

1. For large changes or new features, open an issue first so we can agree on the direction.
2. Fork the repository and create a branch from `main`.
3. Keep each pull request focused on one change.
4. Make sure the build passes:

   ```bash
   mvn -B package
   ```

5. If your change affects how HP is shown or how `/hpview` behaves, also run the Paper test (see [Build](README.md#build)):

   ```bash
   python3 scripts/test-hpview-paper.py
   ```

6. If you change user-facing behavior, update `README.md`. Updating the translated READMEs is appreciated but not required.

## Translations

Command messages live in `src/main/java/dev/spa/hpview/Messages.java`. To add a language, add its messages there and mention the locale in the README.

## License

By contributing, you agree that your contributions are licensed under the [MIT License](LICENSE).
