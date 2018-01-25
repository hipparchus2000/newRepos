using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;

namespace XmlDocRepos
{




public partial class XmlDocRepos : Form
    {
        class ChildFragment
        {
            public string sha { get; set; }
            public bool hasChildren { get; set; }
        }

        class FragmentWithChildren
        {
    	    public string sha { get; set; }
            //public XmlAttributeList attributes { get; set; }
            //public XmlNamespace nodeNamespace { get; set; }
            public List<ChildFragment> children { get; set; }
        }

        class FragmentWithoutChildren
        {
            public string sha { get; set; }
            //public XmlAttributeList attributes { get; set; }
            //public XmlNamespace nodeNamespace { get; set; }
            public string serializedVersion { get; set;  }
        }

        class DocumentEntry
        {
	        public string name { get; set; }
            public string sha { get; set; }
            public DateTime Created { get; set; }
        }

        List<FragmentWithChildren> FragmentsWithChildren;
        List<FragmentWithoutChildren> FragmentsWithoutChildren; 
        List<DocumentEntry> Documents;

        void StoreDocument(string docName, string path)
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(path);
            var rootFragment = WalkTree(xmlDoc);
            
            Documents.Add(new DocumentEntry
            {
                name = docName,
                Created = DateTime.UtcNow,
                sha = rootFragment.sha,
            });

            status.Text = status.Text + "\r\nStored " + docName + " sha " + rootFragment.sha + " total fragments with children " + FragmentsWithChildren.Count()+ " total fragments without children "+FragmentsWithoutChildren.Count() ;

        }

        void ListDocuments(string docName)
        {
            //todo
        }

        void GetDocument(string id)
        {
            //todo
        }

        ChildFragment WalkTree(XmlNode node)
        {

            //store this node
            if(node.HasChildNodes)
            {
                var sha = StoreElementWithChildren(node);
                return new ChildFragment
                {
                    hasChildren = node.HasChildNodes,
                    sha = sha
                };
            }
            else
            {
                var sha = StoreChildlessElement(node);
                return new ChildFragment
                {
                    hasChildren = node.HasChildNodes,
                    sha = sha
                };
            }

        }

        public static string MakeSHA(string value)
        {
            StringBuilder Sb = new StringBuilder();

            using (SHA256 hash = SHA256Managed.Create())
            {
                Encoding enc = Encoding.UTF8;
                Byte[] result = hash.ComputeHash(enc.GetBytes(value));

                foreach (Byte b in result)
                    Sb.Append(b.ToString("x2"));
            }

            return Sb.ToString();
        }
        
        string StoreChildlessElement(XmlNode node)
        {
            var text = JsonConvert.SerializeObject(node);
            var sha = MakeSHA(text);
            var fragment = FragmentsWithoutChildren.SingleOrDefault(x => x.sha == sha);
            if (fragment != null)
                return fragment.sha;
            fragment = new FragmentWithoutChildren { sha = sha, serializedVersion = text };
            FragmentsWithoutChildren.Add(fragment);
            return sha; 
        }

        string StoreElementWithChildren(XmlNode node)
        {
            var list = new List<ChildFragment>();
            foreach (XmlNode childNode in node.ChildNodes)
            {
                var childFragment = WalkTree(childNode);
                list.Add(new ChildFragment
                {
                    hasChildren = childNode.HasChildNodes,
                    sha = childFragment.sha
                });
            }

            var text = JsonConvert.SerializeObject(list);
            //todo add in attributes etc
            var sha = MakeSHA(text);
            var fragment = FragmentsWithChildren.SingleOrDefault(x => x.sha == sha);
            if (fragment != null)
                return fragment.sha;
            FragmentsWithChildren.Add(new FragmentWithChildren
            {
                sha = sha,
                //nodeNamespace = childNode.Namespace,
                children = list,
                //attributes = childNode.Attributes;
            });

            return sha;

        }


        public XmlDocRepos()
        {
            InitializeComponent();
        }

        private void chooseFile_Click(object sender, EventArgs e)
        {
            DialogResult result = openFileDialog1.ShowDialog();
            if (result == DialogResult.OK) // Test result.
            {
                filename.Text = openFileDialog1.FileName;
            }
        }

        private void process_Click(object sender, EventArgs e)
        {
            if (Documents == null)
                Documents = new List<DocumentEntry>();
            if (FragmentsWithChildren == null)
                FragmentsWithChildren = new List<FragmentWithChildren>();
            if(FragmentsWithoutChildren==null)
                FragmentsWithoutChildren = new List<FragmentWithoutChildren>();
            StoreDocument("test", filename.Text);
        }

    }
    
}
