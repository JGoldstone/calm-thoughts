@document
:title OpenClosed predictability, consistentcy, integrity and provenance
:author Joseph Goldstone
:date @date@
:leading 1
:bottom Joseph Goldstone --- OpenClosed --- @date@
:point_size 12
:text

@s1 Principles s1@

Artists should be able to access standard attribute metadata even if there's no 1:1 counterpart in camera vendor metadata, whenever possible. (Obviously, if there's a 1:1 counterpart, that should be available as a standard attribute.) We call this process @i normalization i@.

All vendor-provided metadata should make it to the OpenEXR file, even if it duplicates standard attribute metadata. Nothing gets dropped on the floor.

Runtime introspection should allow artists to filter out vendor provided metadata if it is completely subsumed by standard attribute metadata. This filtering needs to be optional, on by default, but able to be turned off for forensic investigations.

@s1 Outstanding overall design issues (with both OpenEXR and OpenClosed) s1@

@s2 non-frame cadence s2@

There's still no good solution to classic CG compositing happening at a frame cadence, but metadata happening not just at a global-clip or frame cadence but also at a different rate or even asynchronously. Lens metadata from Cooke samples at up to 285 samples per second. And something like camera pan data from a camera head with encoders might return values only when the encoded values differed by some amount, e.g. 1/100th of a degree -- that's not sampling, that's happenstance.

@s2 definitions not linked to normative standards s2@

And there's also a definitional issue. The OpenEXR implementation keeps definitions in two places. First, there's a source C++ header file, @c ImfStandardAttributes.h c@, in the OpenEXR library sources, and this is often taken as ground truth by people writing code against the library. Artisits, on the other hand, probably don't look at C++ source code; they look at @link https://openexr.com/en/latest/StandardAttributes.html :text the OpenEXR documentation on Standard Attributes link@. But neither the C++ source header file nor the documentation have any normative force, and, today, neither seems to reference normative documents.

They should. ISO allows one to search their documents' Terms and Definitions sections for free. The portal is known as the @b ISO Online Browsing Platform b@ and is @link https://www.iso.org/obp/ui :text here link@. The search box on that page can be narrowed down to Terms and Definitions using the radio buttonso above the box. Of course, given the scope of ISO's operations, one must do some nbarrowing down: a search for "focal length" returns definitions of terms containing that string from 24 different ISO documents. Ignoring standards on, @i e.g. i@, light microscopy, ultrasonic testing, nuclear reactors, @i &c i@, one is left with:

@ul
  @link https://www.iso.org/obp/ui^#iso:std:iso:517:ed-3:v1:en:term:2.4 :text ISO 517:2008(en) Photography — Apertures and related properties pertaining to photographic lenses — Designations and measurements link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:19093:ed-1:v1:en:term:3.2 :text ISO 19093:2018(en) Photography — Digital cameras — Measuring low-light performance link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:20954:-1:ed-1:v1:en:term:3.7 :text ISO 20954-1:2019(en) Digital cameras — Measurement method for image stabilization performance — Part 1: Optical systems link@
| @link https://www.iso.org/obp/ui^#iso:std:iso:18383:ed-2:v1:en:term:3.2.1 :text ISO 18383:2025(en) Digital imaging — Specification guideline for digital cameras link@
ul@

Hmmm. This didn't go where I wanted to see it; the definition of focal length that's least related to wonky non-photographic fields is this one:

# @image iso_517_clause_2.4 image@

# with this associated figure:

# @image iso_517_fig_1 image@

The right thing to do here is to communicate with Dietmar Wueller of Image Engineering, who is PL on several documents going through TC 42, to get a reference to an ISO document that would be a useful anchor for OpenEXR's @c ImfStandardAttributes.h c@.

We now return you to the main thread of this document.

@s2 Normalized mappings and pass-through attributes s2@

Including deprecated standard attributes, there are 56 standard attributes in @c ImfStandardAttributes c@ . A couple of years ago the attributes were re-grouped so that related attributes would be in close proximity in the file and here we will try and echo that.

For any particular area (starting below with @c imager c@)

@s3 @c imager c@ s3@

In the Sony proposal, metadata having to do with the sensor would be in a @c acq:imager c@ namespace, in its original, non-normalized form. 

@s4 @c sensorCenterOffset c@ s4@

Type: Imath:V2f

Definition:
@code
//
// sensorCenterOffset -- horizontal and vertical distances, in microns, of
// the center of the light-sensitive area of the camera's sensor from a point
// on that sensor where a sensor surface normal would intersect the center
// of the lens mount. When compared to an image captured with a perfectly
// centered sensor, an image where both horizontal and vertical distances
// were positive would contain more content holding what was at the right
// and what was at the bottom of the scene being captured.
//

code@

@s5 Sony derivation s5@

@i I don't think there's anything in the Sony camera data that can be converted into this directly. It may be possible to tease apart the OpenTrackIO lens distortion model such that the difference between the center of the sensor and the center of the camera side of the mount, and the lens optical center and the center of the lens side of the moiunt, can be distinguished. i@

@s5 ARRI derivation s5@

@i Same as Sony -- there's nothing in the published RDD 55 that would cover this i@

@s5 Discussion s5@

This attribute exists to support the separation of camera-side and lens-side centering offsets for distortion models; in particular it exists to support the portability of a measured lens distortion model across camera bodies, so that if a camera body must be swapped, a production can immediately work rather than waiting for a new combined-camera-body-and-lens distortion measurement session, which is often an overnight process.

@s4 @c sensorOverallDimensions c@ s4@

# and things would continue from here.






document@
