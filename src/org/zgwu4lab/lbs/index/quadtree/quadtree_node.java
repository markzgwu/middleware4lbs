package org.zgwu4lab.lbs.index.quadtree;

import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public class quadtree_node {
	RectBounds rectangle;
	quadtree_node parentnode = null;
	quadtree_node[] childnodes = new quadtree_node[4];
	
}
