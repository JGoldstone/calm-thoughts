@document
:title Comments on Sony's July 2026 metadata proposals
:author Joseph Goldstone
:date @date@
:leading 1
:bottom Joseph Goldstone --- Comments on Sony's July 2026 metadata proposals --- @date@
:point_size 12
:text

@read blue_attr_name.k read@

@s2 Document set as a whole s2@

@s3 Proposal filenames s3@

The filenames raise questions about the difference between a 'specification' and an 'extension'. The three filenames, excluding the version numbers at the end and the .pdf extension, are:
@ul
  OpenEXRAttributesBaseSpecification
| OpenEXRAttributesSourceExtension_RDD18Sony
| OpenEXRAttributesSpecification_OpenTrackIOSourceMapping
ul@

This would be more obvious:

@ul
  OpenEXRAttributesBaseSpecification
| OpenEXRAttributesExtensionSpecification_Sony_RDD18
| OpenEXRAttributesExtensionSpecification_ARRI_RDD55
| OpenEXRAttributesExtensionSpecification_OpenTrackIO
ul@

The inconsistent naming gives a sense of disorder that is not appropriate for something where clearly, many smart people have put in a lot of time. 

It might be justifiable to suggest

@ul
  OpenEXRAttributesExtensionSpecification_Cooke_/i
ul@

but that depends on whether something like @battr acq:sony:cooke:carlZeissAgDataset battr@ is genuinely an implememntation artifact of the same order as @battr acq:sony:cooke:startupCommandLeaderPart battr@ or whether the Carl Zeiss AG data set is independent enough that another camera vendor might be carrying the same data set someday.


@s3 Normalization s3@

In some plalces there are 


@s2 Base specification s2@

@s3 Deferral of use of the 'bytes' type s3@

I feel it is a mistake to represent as strings what really should be represented as bytes, given how long it is going to take to get this stuff implemented. At worst, come up with placeholder string attributes that start life as deprecated entites, to be replaced by bytes as soon as possible. (Obviously, state this more clearly.) If you allow people to do the wrong thing to start with, you will never get them to change to the right thing later.

@s3 §2.1.1 ("Delimiter") s3@

Is the comment abut how the colon character may not be appropriate in all environments related to the inability of macOS filenames to contain color characters?

How often might it be the case that one would want to use the full hierarchical name of an attribute as a filename? Being unfamiliar with modern USD-based workflows I don't have the background, myself, to comment intelligently about this. The one thing I would say is that any estimation of macOS limitations as being unimportant, because traditionally macOS has not been a widely-used platform for VFX, would be incorrect. We cannot today predict how the market will look in ten years, but when storing metadata, we should be thinking about the long term.

@s3 § 2.1.3 ("Top-Level Namespace") s3@

The introduction of a new namespace for metadata is laudable, and provision for hierarchy [...in short, the issue is going to be backwards-compatibility, collisions with existing metadata, and the extra burden placed on the artist]

@s3 § 2.1.4 ("Sub-Namespace") s3@

This section should proscribe the addition of new second-level sub-namespaces without due process. What that due process is, will depend on the nature of whatever organization is charged with realizing this proposal.

Without such a proscription, new metadata creators will surely invent their own second-level namespaces, and both DCC application authors and artists will suffer an additional mental challenge of figuring out why this new metadata couldn't be fit into one of the existing second-level sub-namespaces.

@s3 § 2.1.6 ("Namespace Schema Identification") s3@

What is a "target namespace identifier" in the second paragraph here? The term hasn't been defined yet.

@s3 § 2.2 "Preference for Unified Attributes" s3@

There are several interesting cases here.

One is when a vendor's metadata contains a metadatum that clearly and directly semantically matches an existing un-namespaced OpenEXR attribute with an exact match to the data type fo the un-namespaced OpenEXR attribute. An example might be a string storing the camera firmware version. In this case a strong case could be made for only storing the un-namespaced OpenEXR attribute.

Another is when a vendor's metadata contains a metadatum that semantically matches an existing un-namespaced OpenEXR attribute but the types do not match. Suppose that the vendor's metadata for camera serial number was a simple integer. The OpenEXR standard attribute for camera serial number is a string, which is a more general type. Again a strong case could be made for only storing the un-namespaced OpenEXR attribute.

Yet another case would be if the vendor's metadata contained a metadatum that matched an un-namespaced OpenEXR standard attribute in semantics and in type but not in units, e.g. a 16-bit integer in the original is expressed in meters and there is an un-namespaced OpenEXR attribute that is represented as a 32-bit field expressed in microns.

A more challenging case is this one: a vendor devises a metadatum new to the industry that comes from a unique capability of their equipment. They represent it as a namespaced attribute. The attribute turns out to be very useful. After a couple of years, another vendor figures out how to provide that metadatum from @i their i@ equipment as well. They represent it as a namespaced attribute as well. Is there a process for "hoisting" namespaced attributes to a vendor-less name? Suppose that it becomes possible, indeed neccessary, to generate that attribute synthetically. Does the generator of that attribute put the metadata into a namespace of its own creation? Does it put the metadatum into the namespace of the first vendor that pioneered the metadatum?


Who determines whether a vendor's metadata matches a common concept? This is what camdkit was for. 

@s3 § 3.5.1 "Lens Attributes" s3@

What happens if both the vendor-neutral lens attribute is present, and the OpenEXR Standard Attribute is present, and they have different values? Does one take precedence?

Say that the  

@s2 …SourceExtension_RDD18SDSony… s2@

@s3 § 1.1, "Scope" s3@

This bullet point:
@ul
Sony Private Acquisition Metadata Set Version 1.10 , which is a Sony-specific extension to the SMPTE RDD 18 . This also defines a metadata set for storing Lens Metadata compliant with the Cooke /i Protocol and its extensions.
ul@

seems slightly … off-angle. It does not feel quite right to call Sony Private Acquisition Metadata Set Version 1.10 an extension to RDD 18. It feels like it's an extension to the OpenEXR Base Specification, not to RDD 18. It's an extension to the OpenEXR Base Specification that carries into OpenEXR the metadata carried both by RDD 18, by additional metadata from specific Sony camera systems such as the F65, and by metadata relating to the mechanics of how Sony camera systems interact with Cooke lenses.

@s3 §3.4.1, "Cooke Protocol Lens Metadata Set" s3@

Did you really mean to have an embedded space in @battr acq:sony:cooke:inertialTrackingData Extension battr@?

"Lens Attribute", as a string, feels like a hedge against it being difficult to add new items easily. When strings are used instead of 'real' typed attributes, it can lead to non-standard hacks that become de facto standards. Consider a normalization toolkit. Its job becomes much, much more difficult when arbitrary attributes are carried as strings in some undocumented manner.

@s2 OpenTrackIO s2@


document@

