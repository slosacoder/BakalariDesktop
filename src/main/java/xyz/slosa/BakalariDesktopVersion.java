package xyz.slosa;

public final class BakalariDesktopVersion {
    private final String title; // Display window title
    private final String semanticVersion, branch, commitSHA;
    BakalariDesktopVersion() {
        final Package manifest = Client.class.getPackage();
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

        this.title = String.format("BakalariDesktop (%s-%s/%s-%s)",
                semanticVersion, commitSHA, System.getProperty("os.name").toLowerCase(), branch);
    }

    @Override
    public String toString() {
        return title;
    }
}
