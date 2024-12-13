package xyz.slosa;

public final class BakalariDesktopVersion {
    private final String title, version; // Display window title
    private final String semanticVersion, branch, commitSHA;

    public BakalariDesktopVersion() {
        final Package manifest = BakalariDesktopClient.class.getPackage();
        final String versionString = manifest.getImplementationVersion(); // 0.0.0-000000-main

        // Running from IDE
        if (versionString == null) {
            semanticVersion = "internal";
            commitSHA = "unknown";
            branch = "dev";
        } else {
            semanticVersion = versionString.split("-")[0];
            commitSHA = versionString.split("-")[1];
            branch = versionString.split("-")[2];
        }

        this.version = String.format("%s-%s", semanticVersion, commitSHA);
        this.title = String.format("BakalariDesktop (%s-%s/%s-%s)",
                semanticVersion, commitSHA, System.getProperty("os.name").toLowerCase(), branch);
    }

    @Override
    public String toString() {
        return version;
    }

    public String getTitle() {
        return title;
    }
}
