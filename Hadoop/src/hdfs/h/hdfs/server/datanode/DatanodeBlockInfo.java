/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package h.hdfs.server.datanode;

import h.hdfs.server.datanode.FSDataset.FSVolume;

import java.io.File;

/**
 * Datanode的Block的信息封装
 */
@SuppressWarnings("unused")
class DatanodeBlockInfo {

	// 数据块保存的数据目录
	private FSVolume volume;
	// 数据库对应的具体文件
	private File file;
	// 是否写时复制
	private boolean detached;
	
}
