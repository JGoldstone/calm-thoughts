# What's OpenClosed?

OpenClosed is a proposed new Academy Software Foundation project for normalized metadata across the clip-to-frame sequence boundary, including APIs for name and value normalization and consolidation, identification of semantically equivalent metadata, identification of metadta affected by a set of identified operations (e.g. resizing or cropping), and for comparing before-and-after sets of metadata to see what was dropped.

OpenClosed is intended to be supported by equipment vendor and metadata consumers alike. The response to dark metadata, in the medium to long term, should not be "let's be sure to throw this away in case we've invalidated it" but "let's partner vendors and consumers together, to bring light to dark metadata".

OpenClosed is big on registries of names. It would provide APIs to get SMPTE Item names for metadata in MXF headers, and it would have registered ways to map those ULs, based on their MXF class 14 namespaces or more complex rules for legacy or signed-over-to-SMPTE-ULs, into string names. Note, though, that this doesn't mean these deduced or explicitly mapped names would appear as OpenEXR attributes; see the subsection "Name normalization" under "What would it do?" below.

The OpenClosed project would work with the SMPTE 30MR (metadata registries) technical committee, so that when that group released a revision of the metadata registers, the OpenClosed project would update its embedded registries and put out a new release.

_[to be fleshed out]_ Supported but non-mainstream APIs would allow vendors to create pre-release metadata and consumers to understand it, while clearly labeling the pre-release metadata as such. If it turns out the pre-release metadata is semantically equivalent, sufficiently accurate, and otherwise usable, OpenClosed will provide mechanism to recognize such pre-release metadata as release-level metadata. If it turns out that this early metadata was flawed, no such 'promotion' would be contributed, and someone coming back to a clip with pre-release metadata in three or four years wouldn't confuse what was there with comparable final-form metadata.

There's nothing _per se_ in this architecture that says "Only MXF can be a source". An MP4 could be a source; a series of DPX or ARRIRAW files could be a source. But the industry is MXF-centric at this point, and so the first focus for OpenClosed should be MXF -> OpenEXR. This is the emphasis of the recent (July 2026) Sony proposals, and they are huge steps forward in the right direction.

_[to be fleshed out]_
At a minimum, OpenClosed needs to be able to take two pieces of metadata A and B, each with names and values, and be able to say "is B the generic form of A"? In the Sony documents, they speak of converting generic MXF metadata and Sony RDD 18-style metadata into OpenEXR attributes. They suggest that it be permissible for an OpenEXR file to carry mulltiple metadata that is semantically equivalent, e.g. `acq:rdd18:lens:irisTNumber`, `acq:lens:irisTStop` and `tStop`. With OpenClosed, we can express this in code. We can have a predicate that answers the question "is B more generic than A?". We can have a function that answers "what is the most generic form of A?". For forensics, we can even have a function that says "what is the explanation of how A is converted to B?" which might return something saying it was a straight copy, a conversion from lens serial float to IEEE 754 float, or even an override of a theoretical parameter (e.g. lens distortion coefficients from an optical model are being overridden by lens distortion coefficients from regression of captured charts).

Beyond APIs, there would be a command-line tool to compare two files (typically a 'before' and an 'after' file) and determine what was dropped, e.g. after reading a clip into an editor, removing one frame, and writing out the result. There would be standard reference clips, provided by camera and lens vendors, to help determine where in a show pipeline metadata from the set was lost on the way to the artist.

## Is there precedent for what OpenClosed would do?

OpenClosed has roots in two projects: an ARRI project known as the ARRI Metadata Bridge (the AMB); and a SMPTE Rapid Industry Solutions (RIS) project targeted at on-set virtual production (OSVP), known as `camdkit`.

- The AMB was a technical success but ultimately was abandoned. It was discontinued without explanation, not only to its users, but to its author and maintainer. OpenClosed incorporates a path for revenue for contributors that require it -- yes, _keep reading_ -- but does not provide exclusitivity, so a contributor that withdraws for financial or other reasons can be compatibly replaced.

- `camdkit` is a successful RIS for OSVP project, but is written in Python and is unsuitable as such for direct incorporation into such C++-based ASWF projects such as OpenImageIO, OpenTimelineIO, OpenColorIO and possiblly others.

## Relationship to the Sony OpenEXR mapping proposal

The Sony proposal does many things: it puts forth a plausible, vendor-independent proposal on how to map metadata in the MXF Generic Container into OpenEXR, covering structural, static and dynamic metadata; it advocates a naming scheme to avoid naming collisions[1^]; and its proposed namespace is extensible.

But without something like OpenClosed, the Sony mapping is a set of documents examined and understood independently by each transcoding tool's vendor, at varying levels of completeness and correctness, and the cadence of fixes to the mapping is that of the transcodign tool's release cycle.

With OpenClosed, the Sony mapping is behavior, enforced by Sony's own signed reference clips, reference OpenEXR outputs and reference tolerance specifications, with that behavior being identical across Resolve, Transkoder, Daylight and FFmpeg toolsets using the same OpenClosed release.

The logical next platform after Sony is not ARRI, by the way. It's Apple. The idea that a visual effects supervisor can prototype a metadata workflow with a $1.5K device they already have in their pocket is even more powerful than the idea that they could shoot some of their next feature's scenes on a $5K Sony FX5 they could buy at B&H Photo Video. Someone could walk around the NAB 2027 show floor with their phone, take it to the Colorfront booth, and get that shot as a set of OpenEXR frames fully populated with metadata[^2].

[^1]: The AMB did something similar, in that it used com.arri.{camera, lens}.xxx namespaces.
[^2]: Alternatively, if Sony wanted to go there, that visual effects supervisor could go to the Sony booth, show on their iPhone 18 Pro Max a clip they'd produced, get it both transcoded into OpenEXR for VFX and P3-D65 HEIC tonemapped for HDR display on their phone, then hand over that same iPhone 18 Pro Max for an hour, and get a loaner FX5 body and basic lens with which to repeat the process. BMD is probably the only other camera vendor with a price point that would allow this.

## More about the name 'OpenClosed'

The "Closed" portion of the name stands for "Camera", "Lens", "On-Set", "External" and "[meta]Data".

The "Open" part indicates that the project _architecture_ is open, and that the core project _code_ is freely available, and that reference _images_ and _clips_ are freely available, even though _vendor plugins_ may be closed-source and licensed.

## Wait, what? _Closed-source_? And _licensed_ (!!) ?

Yes. Read the next section.

# Why vendors stay

Most companies involved with the Academy Software Foundation (ASWF) sell software that runs on commodity hardware. I don't know of any ASWF members that have launched ASWF projects that require the member's proprietary hardware, but there are hybrids, _e.g._ Apple has contributed code to exploit Apple-hardware-specific acceleration of ASWF libraries, and doubtless others (NVIDIA, etc.) have as well. This is a valuable and freely donated public good.

But what's peculiar to the camera and lens metadata space is that the companies making the products generating the metadata started as hardware companies. For those companies, software product support was a 
in the largely pre-digital era, and are run by older executives who don't see how one can make money by gving things away.

Any project to improve the lives of artists, by getting camera and lens metadata to them through software that normalizes and makes metadata interpretation choice consistent across the desks of hundreds of artists on  variety of platforms, needs continuing vendor support as new cameras and lenses come along.

But vendors will pull out if they don't feel compensated. Or in control. (_Q._ What? _A._ Read on!)

## The need for money and the need for control

Any vendor that doesn't have full management support for open-source contributions will drop out at their first business downturn, that being a convenient excuse. Likewise, if there is investment required, the vendor will cite 'the bottom line' and refuse to sign off on needed equipment (e.g. perhaps the vendor CI group might need a graphics card upgrade).

Likewise, if the vendor feels as if a project being open-source means they no longer have control, in this case of how camera output metadata is converted to DCC-app-consumable OpenEXR metadata, that vendor will back out. The conversion process can entail some tricky corner cases or some non-obvious constraints (e.g. for best results, do all math involving a particular metadatum with rationals, and only convert at the end). Vendors want to be sure conversions happen correctly and consistently.

The correct way to deal with this is _not_ to say that only equipment vendors can write plugins to handle their camera output. The correct way to deal with this is to say that only equipment vendors can certify correct output, by cryptographicallly-signed reference input clips and cryptographically-signed attribute-laden reference OpenEXR files. The vendors contribute their public keys for reference input and reference output to the project, as well as their own processing plugins.

Other plugins are welcome, but must produce the same output. Plugins can be closed-source. If you can develop a plugin that takes three lens ring rotational positions from public lens Y metadata, and derives something useful like pinhole focal length from them, and you do this in one-tenth the time the vendor's plugin takes to produce the same output, then people will want to pay you for saved time. The lens vendor can either suck it up, or improve their game.

_[to be fleshed out]_ When we say 'must produce the same output', the equipment vendor can also determine what 'the same' means. For a string, by default, it must match character by character[^2]. For a float that is the result of a units conversion, 'the same' might mean within, say, `4 * std::numeric_limits<float>::epsilon()`.

[^2]: check: for UTF-8, does a character match imply a byte match, or are there multiple bytes sequences that can produce the same character?

Either way, whether they are paying someone or using a donated plugin, the artist knows with fair probability that they are getting valid `pinholeFocalLength`, because they know neither the lens vendor plugin nor the third-party plugin would have been distributed as part of a tagged OpenClosed release if the distributed plugin didn't produce an in-tolerance match to the cryptographically-signed vendor output from the cryptographically-signed vendor input.

DCC apps might want to, in some cases, differentiate which plugin was used for which OpenEXR metadatum. Maybe one of the inputs used for the third-party app is a number that comes, not from lens design data, but from measured lens manufacturing variation data. This is a case where the third-party app would either supply metadata with a unique attribute name (`measuredFooDistortion`) alongside a standard attribute name (`fooDistortion`), or rewrite the standard attribute name but mark the provenance with the name of the measurer.

# The OpenClosed library

While it's premature to propose API at the level of `class`es, `struct`s, `enum`s, top-level functions, etc., it's reasonable to set down some desiderata.

## The OpenClosed API itself

OpenClosed would use the full power of modern OpenEXR. Define attributes that are sequences of bytes as an `Imf::BytesAttribute`, not an `Imf::StringAttribute`. If compatibility is a concern, create a string attribute that can be converted to a bytes attribute, emit ideally both but if need be just the string attribute, then rely on OpenClosed to recognize and normalize the attribute to bytes. At the same time as the string attribute is introduced, use OpenEXR's ability to deprecate attributes, marking it from the beginning as a stopgap.

It would be built using the most modern C++ version that the principal clients can support, probably C++17, and would avoid constructs that are known to be deprecated in later C++ releases.

And it would implement the API as an OpenFX API.

## Target markets and deployment strategies

OpenClosed is targeted to be picked up by two different markets. First, as was the case with the AMB, it should be linked (statically or dynamically) into the three primary platforms for digital cinema camera ingest: Resolve, Colorfront's Transkoder, and Filmlight's DayLight/Baselight. Second, and brand-new, an open-source alternative based on vendor or third-party ffmpeg thin shims, driven by an enhanced OpenImageIO that with OpenClosed backing it would no longer shy away from full metadata extraction.

Though some of OpenClosed would be available via the Python scripting built into OpenImageIO, OpenClosed would have its own Python bindings implemented with `nanobind`, which is the modern alternative to `pybind11` that OpenImageIO now includes as an alternative binding.

Next we set down some core library functionality.

## Name normalization

Most generically, there are OpenEXR standard attributes. A few are required (`dataWindow`, `displayWindow`) but most are optional (`nominalFocalLength`, `lensFirmmwareVersion`).

### Name normalization policy

Say that one has a vendor-neutral name (`acq:camera:model`) and there is an OpenEXR standard attribute that is semantically equivalent. OpenClosed would provide an API to ask "Is there a standard attribute with identical semantics (same meaning, same units, same range, same precision) and if so, what is it called?". The API would return the UTF-8 string "cameraModel".

Now suppose there is a vendor-specific name (`acq:arri:lens:axialEffectiveFocalLength`) and there is a semantically equivalent OpenEXR standard attribute. That same API should take the vendor-specific name and return the UTF-8 string `effectiveFocalLength`.

There will be metadata normalization possibilities that seem tempting but may not be such a good idea. If an attribute is carried in MXF as a rational, yes, you _could_ convert it to a float, which is a more familiar type for OpenEXR artists to deal with. (Can Nuke even handle arithmetic expressions where the inputs are rational metadata? Can Fusion?) But in all likelihood there won't be perfect round-tripping, and if you don't think being off by one on a round-trip is a big deal, I encourage you to talk with people on the ASC Frame Decision List project for a couple hours to see if you can hold your opinion against their sentiment.

### Normalizing obsolete names

An interesting case is one in which a name starts off as vendor-specific and, though never adopted into the OpenEXR standard attribute set, becomes vendor-neutral.

Currently the Sony proposal (_cf._ OpenEXRAttributesSourceExtension_RDD18Sony(Draft)v050d3.pdf) includes a metadatum known as "Rotary Shutter Mode", of type unsigned char with an OpenEXR attribute name `acq:sony:f65:rotaryShutterMode`. Let us say that OpenClosed ships a 1.0 release and a Sony-provided plugin reading F65 output normalizes the above output so that an attribute with name `acq:sony:f65:rotary:ShutterMode` is indeed present in the OpenEXR header.

Now suppose that ARRI revived the "ALEXA Studio" brand with a modernized sensor, etc. (granted, _extremely_ unlikely prospect). Little-known fact: SMPTE RDD 30 included an ARRIRAW header attribute, DeviceInformationFlags, the low-order bit of which was 1 when a camera had a mirror shutter and it was active.

If the unsigned char value for `acq:sony:f65:rotaryShutterMode` had only **On** and **Off** values, and this was seen as a semantic match to what was happening with the ARRI camera, then there is a case to be made that this commonality deserves recognition in attribute naming: both the Sony-provided and ARRI-or-ARRI-user-community-provided OpenClosed plugins would normalize their respective metadatum to have a name like `acq:camera:rotaryShutterActive`.

In fact even if the unsigned char value for `acq:sony:f65:rotaryShutterMode` has more than two modes, as long as there are modes for both completely inactive and completely active, both vendors' metadata can still be normalized to `acq:camera:rotaryShutterMode` but the defined value in OpenClosed would have to be interpreted more as a C enum rather than a bool.

The OpenEXR TSC has already flagged attributes as deprecated; as can be seen [here](https://openexr.com/en/latest/StandardAttributes.html#deprecated-attributes), the current list is `dwaCompressionLevel`, `renderingTransform`, `lookModTransform` and `maxSamplesPerPixel`. Currently this list only exists as documentation; I strongly suspect that if OpenClosed requested the OpenEXR library be extended to allow the set of deprecated attributes to be queried, the extension would be made.

## Name filtering

OpenClosed API should provide both the ability to see all attributes, or introspect only those that haven't been normalized to produce a more generic result. Using the examples mentioned above, in the filtered view, one would see:

- `cameraModel`
- `effectiveFocalLength`
- `acq:camera:rotaryShutterActive`

and in unfiltered mode one would see

- `cameraModel`
- `effectiveFocalLength`
- `acq:camera:rotaryShutterActive`
- `acq:sony:f65:foraryShutterMode`

When everything was going well, artists would only see the three. When something was an issue, artists or an accompanying technical director could use unfiltered mode forensically.

### Create an OpenEXR header

For an ingest vendor, this would be the starting point. One can't modify the header in an existing OpenEXR file without rewriting the file, because of the way the file is layed out; but one can build up a header incrementally.

An ingest vendor would have on hand:

- the MXF files containing camera metadata

- from an ordered sequence of SMPTE KLV-encoded attributes
  (should this really be an ordered sequence? Is order important? If not, passing a set instead of a sequence would make it more clear that order was not important.)
- from a sequence of attribute name and attribute value pairs
  (what is the clean way to retain the type of the value element in the pair, using the most reasonable choice for a C++ version for an ASWF project with the anticipated clients?)
- normalize attribute names

## alter an OpenEXR header

- 

- example: convert integer camera serial number to UTF-8 cameraSerialNumber attribute
- example: convert imperial focus distance to metric

### perform Pydantic-style extended type checking at attribute assignment time

- example: the weight of a lens cannot be negative

### express metadata dependencies

- example: you take acq: sony: rdd18: 

### express metadata 

# Relationships to other projects, non-ASWF and ASWF

## ffmpeg (non-ASWF)

All ARRI and most Sony DCC-app-consumed camera output these days is in MXF format. There is only one even half-maintained MXF-processing tool out there right now, and that is `ffmpeg`. But the stock `ffmpeg` implementation can't handle MXF generated by ARRI, and it probably can't handle MXF generated by Sony's Cine Alta line.at least some DCC camera output, e.g. I don't believe it can handle the MXF described in SMPTE RDDs 54 (ARRIRAW) or 61 (ARRICORE). I don't know about others.

The New York Times [published an article] (https://www.nytimes.com/2026/04/15/opinion/mythos-open-souce-internet.html) on the criticality of `ffmpeg` and vulnerabilities found in it by Anthropic's Mythos.

The [Sovereign Tech Agency] (https://www.sovereign.tech/), funded by the German government, is investing in `ffmpeg` to make it more resilient, as a digital infrastructure project. They are not kidding around; their 2026 in investment is listed as €280,350).

I would suggest that if a camera vendor's MXF output isn't yet supported by `ffmpeg`, they look for external funding (from, e.g., Germany's Bundesministerium für Digitales und Staatsmodernisierung) to get it supported. If they are still giving grants, e.g. for 2027, there is zero reason to believe that this arm of the German government would limit grants to companies based in Germany.

This would also finally solve a longstanding complaint from a certain extremely large VFX house that there was no support in OpenImageIO for the output of certain dominant brands of DCC camera.

## OpenColorIO (ASWF)

OCIO is already dealing with the possibilty of multiple markers for a colorspace being present in the clip or file(s): embedded ICC profiles with recognizable combinations of white point, OETF or EOTF, primaries and/or CICP, file extension-based rules, and so on.

## OpenFX (ASWF)

The plugin architecture should be built on OpenFX. Plugin authors would write in C++; but the actual calls into OpenFX are C-api calls, which would isolate plugin authors from year-to-year changes to mandated C++ support in the VFX Reference Platfor.

Pierre Jasmin, who I know from Academy SciTech committee work, is one of the most practical people I know, and completely dedicated to improving the quality of both the visual effects themselves and the lives of the artists that produce them.

Phil Barrett, one of the five voting members of the OpenFX TSC, is probably both the single most capable user of the old ARRI Metadata Bridge, and the one most burned by it. While at ARRI, under the direction of staff no longer there, I was left uable to answer his queries of "will there be Apple Silicon support for the AMB?". He ended up replicating its functionality in proprietary (and multi-platform) code.

## OpenImageIO

_This needs Larry's eyes, and I need to speak with someone, probably Zach Lewis, on color-related aspects_

Comment on OpenTrackIO discussions re: CCT and tint, with especial notes of comments from Tucker Downs and Uday Mathur on why tint is undefined at the ends of the linearly interpolated Blackbody/daylight crossover.

# Next Steps

## New concordance spreadsheet

There needs to be a new concordance spreadsheet, that tries to find the best semantic matches between the current camera lines from Sony, ARRI, RED, Blackmagic Design, FUJIFILM, and Apple. Since I did the original concordance spreadsheet a decade ago, I'm the best person to do this. Sources for each:

- Sony: the published RDD 18 supplemented by the proposed mapping to OpenEXR attributes.
- ARRI: the published RDD 55. If and when RDD 55's revisions are in public CD, then that metadata as well.
- RED: I will need some help here. Does anyone have a contact at RED who can help with this?
- Blackmagic Design: I don't know where their documentation is, and what we want is manifold:
  - traditional metadata comparable to what is seen in RDD 18 and RDD 55
  - unique metadata related to the immersive video camera they have built, used extensively by Apple
- FUJIFILM: I don't know where their documentation is; we want the metadata output by the camera they introduced at IBC 2025
- Apple: I would look on their developer site first; if I can't find anything in the way of comprehensive metadata there, I would ask contacts at Apple, who I know through ISO TC 42 work, for pointers
- OpenLensIO: the camera model developed by the SMPTE RIS for OSVP, published [here](https://github.com/SMPTE/ris-osvp-metadata-camdkit/blob/main/src/main/resources/res/OpenLensIO_v1-0-1.pdf).

The concordance spreadsheet frames debate about how to normalize camera vendor metadata into OpenEXR standard attributes, or attributes with acq: level 3 names (_e.g._ `acq: imager: averageSensorTemperature`).

## SMPTE RIS for OSVP camera and lens MD restart

There _was_ an effort inside SMPTE RIS to turn products of the camera and lens metadata group inside the On-Set Virtual Production RIS project into an ASWF project. David Morin told me that effort "just faded away". I am tryng to determine just what the scope of the pitch to David was. Whicih of these were included in the pitch?
1. `camdkit`, the Python package that normalized camera and lens metadata from several camera systems, with rigorous value checking provided with Pydantic?
2. **OpenLensIO**, the camera and lens model that strove to rigorously define camera and lens terms in line with what the VES and Cooke had published in [Camera and lens definitions for VFX](https://cookeoptics.com/wp-content/uploads/2023/07/Cooke-Camera-Lens-Definitions-for-VFX-210723.pdf) just in time for SIGGRAPH 2023?
3. 
Did it include the data models for tracking system information?
Did it include the transport wrapping using SMPTE ST 2110-44 that was being prototyped by Roberto before his house burned down in the January 2025 Los Angeles fire?

## OpenTimelineIO

... this is an area where really Sam Richards can help me fill this out ...

## USD utility

The VFX world is coalescing around USD, and clearly it is critical that the metadata normalized here are cleanly and elegantly carried into that world; but it is a world of which I am largely ignorant, having started out on the color and not the geometry side of things. It seems very clear that the Sony people understand USD, cold. Ideally they can be drawn into this ASWF project, in many of its component areas, but especially with regard to USD their contributions could be immense.

