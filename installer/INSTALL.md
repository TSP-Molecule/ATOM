<b>Installation Instructions</b>

Below we have listed how to install ATOM for both Windows and Unix systems. Thank you for trying out our program.

<b>Pre-requisites</b>

We are assuming that you have Java 8+ installed, and Python 3.6.x (on Windows please install to Program Files and not %AppData%.)

<b>Windows Installer</b>

1) Download the installer *here* and run it.
2) Make sure the second check box is ticked to install the python dependencies.<br/>
(Certain features won't work if this box is not ticked).

<b>Windows Manual install</b>

1) Download zip *here* and extract to Program Files (x86)<br/>
(This is the suggested directory, but you can install to any directory).
2) Open an administrative PowerShell.
3) Run <code>python -m pip install chemspipy</code>.

<b>Unix Installer</b>

1) Download the installer *here*, move it to the directory that you want to install to, and run it. (<code>sudo ./ATOM-setup.run</code>)
2) <code>cd</code> into the ATOM directory. Run <code>sudo chmod +x ATOM.jar</code>. This enables the run bit of the file.

<b>Unix Manual Install</b>

1) Download zip *here* and extract into your install directory.
2) In terminal, run <code>sudo pip install chemspipy</code>

<b>What if I don't have Admin privilege?</b>

When installing the <code>chemspipy</code> dependency, just add the <code>--user</code> flag after the keyword <code>install</code>, which will install this python dependency for just this user.
