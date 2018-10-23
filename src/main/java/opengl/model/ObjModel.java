package opengl.model;

import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.momchil_atanasov.data.front.parser.IMTLParser;
import com.momchil_atanasov.data.front.parser.IOBJParser;
import com.momchil_atanasov.data.front.parser.MTLColor;
import com.momchil_atanasov.data.front.parser.MTLLibrary;
import com.momchil_atanasov.data.front.parser.MTLMaterial;
import com.momchil_atanasov.data.front.parser.MTLParser;
import com.momchil_atanasov.data.front.parser.OBJDataReference;
import com.momchil_atanasov.data.front.parser.OBJFace;
import com.momchil_atanasov.data.front.parser.OBJMesh;
import com.momchil_atanasov.data.front.parser.OBJModel;
import com.momchil_atanasov.data.front.parser.OBJNormal;
import com.momchil_atanasov.data.front.parser.OBJObject;
import com.momchil_atanasov.data.front.parser.OBJParser;
import com.momchil_atanasov.data.front.parser.OBJTexCoord;
import com.momchil_atanasov.data.front.parser.OBJVertex;

public abstract class ObjModel extends ModelObject {

	private List<MTLMaterial> mlts = new ArrayList<MTLMaterial>();
	private int myList = 0;

	public abstract String getSourceDir(); // dir

	public abstract String getObjFile(); // obj file

	protected void load() {
		if (getSourceDir().isEmpty() || getObjFile().isEmpty())
			return;

		try {
			InputStream in = new FileInputStream(getSourceDir() + getObjFile());

			// Create an OBJParser and parse the resource
			final IOBJParser parser = new OBJParser();
			final OBJModel model = parser.parse(in);

			final IMTLParser mtlParser = new MTLParser();

			// ======== load materials =======
			for (String libraryReference : model.getMaterialLibraries()) {
				final InputStream mtlStream = new FileInputStream(getSourceDir() + libraryReference);
				final MTLLibrary library = mtlParser.parse(mtlStream);

				mlts.addAll(library.getMaterials());
			}

			// ====== start list ==========

			myList = gl.glGenLists(1);
			gl.glNewList(myList, GL_COMPILE);

			for (OBJObject object : model.getObjects()) {
				for (OBJMesh mesh : object.getMeshes()) {
					MTLMaterial m = getMaterial(mesh.getMaterialName());
					SimpleTexture mytexture = null;
					
					if (m instanceof MTLMaterial) {
						String textureName = "";
						if ( m.getAmbientTexture() instanceof String )
							textureName = m.getAmbientTexture();
						else if ( m.getDiffuseTexture() instanceof String )
							textureName = m.getDiffuseTexture();
						else if ( m.getSpecularTexture() instanceof String )
							textureName = m.getSpecularTexture();

						if ( textureName instanceof String && !textureName.isEmpty()) 
							mytexture = new SimpleTexture(getSourceDir() + textureName ); // getWallTexture()
						
						gl.glColor4f(m.getAmbientColor().r, m.getAmbientColor().g, m.getAmbientColor().b, 0); //
						gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, getFloatBuffer(m.getAmbientColor()));
						gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, getFloatBuffer(m.getDiffuseColor()));
						gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, getFloatBuffer(m.getSpecularColor()));
						gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, m.getSpecularExponent());
						// gl.glMaterialfv( GL_FRONT_AND_BACK, GL_EMISSION, m.getSpecularExponent() );

					}
					
					if ( mytexture instanceof SimpleTexture ) 
						mytexture.on();
					
					for (OBJFace face : mesh.getFaces()) {
						gl.glBegin(GL_TRIANGLE_FAN); // of the pyramid
						for (OBJDataReference reference : face.getReferences()) {
							if (reference.hasNormalIndex()) {
								final OBJNormal normal = model.getNormal(reference);
								gl.glNormal3f(normal.x, normal.y, normal.z);
							}
							if (reference.hasTexCoordIndex()) {
								final OBJTexCoord texCoord = model.getTexCoord(reference);
								gl.glTexCoord2f(texCoord.u, texCoord.v);
							}

							final OBJVertex vertex = model.getVertex(reference);
							gl.glVertex3f(vertex.x, vertex.y, vertex.z);
						}

						gl.glEnable(GL_NORMALIZE);
						gl.glEnd(); // of the pyramid
					}
					
					if ( mytexture instanceof SimpleTexture ) 
						mytexture.off();
				}
			}

			gl.glEndList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private FloatBuffer getFloatBuffer(MTLColor c) {
		float[] a = { c.r, c.g, c.b };
		return FloatBuffer.wrap(a);
	}

	private MTLMaterial getMaterial(String name) {
		for (MTLMaterial m : mlts) {
			if (m.getName().equals(name))
				return m;
		}
		return null;
	}

	protected void drawModel() {
		if (getSourceDir().isEmpty() || getObjFile().isEmpty())
			return;
		
		gl.glCallList(myList);
	}
/*
	private void SetFaceRenderType() {
		final int temp[] = (int[]) fv.get(0);
		if (temp.length == 3) {
			FaceFormat = GL_TRIANGLES; // the faces come in sets of 3 so we have triangular faces
			FaceMultiplier = 3;
		} else if (temp.length == 4) {
			FaceFormat = GL_QUADS; // the faces come in sets of 4 so we have quadrilateral faces
			FaceMultiplier = 4;
		} else {
			FaceFormat = GL_POLYGON; // fall back to render as free form polygons
		}
	}
*/

}
