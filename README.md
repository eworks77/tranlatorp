# tranlatorp
A way to translate language xml files for CMSs.

1. Get as template/source one of the official xmls (ex: en-EN.xml)
2. Translate the entire file with Google Translate. This will translate the texts that interest us. The tags should be not translated.
3. The TransP Java tool will extract the corresponding text for the tags.
4. Will write the new xml with the translated text.
