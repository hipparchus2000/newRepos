using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace XmlRepo
{
    public static class XmlHelper
    {
        public static string? CalculateSha256(string value)
        => String.Concat(SHA256.HashData(Encoding.UTF8.GetBytes(value))
            .Select(item => item.ToString("x2")));
        public static int StoreXmlText(string fileName, string version, string value)
        {
            var rootNode = XElement.Parse(value);
            BaseObject baseElement = ConvertXmlToBaseObject(rootNode);
            var repoFile = new RepoFile(fileName,version, baseElement);
            int count = Repo.AddTopLevel(repoFile);
            return count;
        }

        internal static string GetXmlText(string text, string version) => Repo.GetTopLevel(text,version);
        
        private static BaseObject ConvertXmlToBaseObject(XElement node)
        {
            var attributes = node.Attributes()
                .Select(x => new Attribute(x.Name.ToString(),x.Value)).ToList();
            var childList = new List<object>();
            foreach(var childNode in node.Nodes())
            {
                switch(childNode.NodeType)
                {
                    case System.Xml.XmlNodeType.Element:
                        childList.Add(ConvertXmlToBaseObject((XElement)childNode));
                        break;
                    case System.Xml.XmlNodeType.Text:
                        var characterData = new CharacterData(childNode.ToString());
                        childList.Add(characterData);
                        break;
                }
            }
            var baseObject = new Element(node.Name.ToString(), attributes, childList);
            return baseObject;
        }
    }

}
