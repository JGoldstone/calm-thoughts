@document
:title Camera and lens metadata (CALM) consistency, integrity and provenance
:author Joseph Goldstone
:date @date@
:leading 1
:bottom Joseph Goldstone --- CALM consistency, integrity and provenance --- @date@
:point_size 12
:text

@s3 Assumptions s3@

The first assumption is that it's easier for artists to write portable metadata-using code, Nuke gizmos, whatever if they can use the OpenEXR standard attributes.

The second assumption is that, in the spirit of never dropping metadata on the floor, all the metadata that was present in the original content (.mxf or .mov or .mp4 clip, .ari or .dpx or .tif sequence) should be present in the frame, both to support new camera / lens / on-set extended data that's unrelated to any OpenEXR attribute but also to support metadata forensics. This includes static clip-level metadata (e.g. what show? what shot? what take?) and also, conceivably, oversampled dynamic metadata (see 'Outstanding overall issues' below).

@s3 Outstanding overall issues s3@

@s4 non-frame cadence s4@

There's still no good solution to classic CG compositing happening at a frame cadence, but metadata happening not just at a global-clip or frame cadence but also at a different rate or even asynchronously. Lens metadata from Cooke samples at up to 285 samples per second. And something like camera pan data from a camera head with encoders might return values only when the encoded values differed by some amount, e.g. 1/100th of a degree.

@s4 definitions not linked to normative standards s4@

And there's also a definitional issue. The OpenEXR implementation keeps definitions in two places. First, there's a source C++ header file, ImfStandardAttributes.h, in the OpenEXR library sources, and this is often taken as ground truth by people writing code against the library. Artisits, on the other hand, probably don't look at C++ source code; they look at @link https://openexr.com/en/latest/StandardAttributes.html :text the OpenEXR documentation on Standard Attributes link@. But neither the C++ source header file nor the documentation have any normative force, and, today, neither seems to reference normative documents.

They should. ISO allows one to search their documents' Terms and Definitions sections for free. The portal is known as the @b ISO Online Browsing Platform b@ and is @link https://www.iso.org/obp/ui :text here link@. The search box on that page can be narrowed down to Terms and Definitions using the radio buttonso above the box. Of course, given the scope of ISO's operations, one must do some nbarrowing down: a search for "focal length" returns definitions of terms containing that string from 24 different ISO documents. Ignoring standards on, @i e.g. i@, light microscopy, ultrasonic testing, nuclear reactors, @i &c i@, one is left with:

@ul
  @link https://www.iso.org/obp/ui^#iso:std:iso:517:ed-3:v1:en:term:2.4 :text ISO 517:2008(en) Photography — Apertures and related properties pertaining to photographic lenses — Designations and measurements link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:19093:ed-1:v1:en:term:3.2 :text ISO 19093:2018(en) Photography — Digital cameras — Measuring low-light performance link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:20954:-1:ed-1:v1:en:term:3.7 :text ISO 20954-1:2019(en) Digital cameras — Measurement method for image stabilization performance — Part 1: Optical systems link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:18383:ed-2:v1:en:term:3.2.1 :text ISO 18383:2025(en) Digital imaging — Specification guideline for digital cameras link@
ul@

Hmmm. This didn't go where I wanted to see it; the definition of focal length that's least related to wonky non-photographic fields is this one:

@image iso_517_clause_2.4 image@

with this associated figure:

@image iso_517_fig_1 image@

The right thing to do here is to communicate with Dietmar Wueller of Image Engineering, who is PL on several documents going through TC 42, to get a reference to an ISO document that would be a useful anchor for OpenEXR's ImfAttributes.h.



document@
