using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Intrinsics.Arm;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace XmlRepo
{

    public interface BaseObject
    {
        public string Sha256 { get;  }
    }

    public class RepoFile : BaseObject
    {
        public string Type = "RepoFile";
        public string Name { get; }
        public string Version { get; }
        public BaseObject BaseObject { get; } //root node of content
        public string Sha256 { get; }

        string BaseObject.Sha256 => Sha256;

        public RepoFile(string name, string version, BaseObject baseObject)
        {
            this.Name = name;
            this.Version = version;
            this.BaseObject = baseObject;
            var list = new List<string> { name, version, baseObject.Sha256 };
            string combined = string.Join(",", list);
            this.Sha256 = baseObject.Sha256;
        }

    }

    public class Attribute : BaseObject
    {
        public string Type = "Attribute";
        public string Name { get; }
        public string Value { get; }

        public string Sha256 { get; }

        public Attribute(string name, string value)
        {
            this.Name = name;
            this.Value = value;
            string combined = name + ":" + value;
            this.Sha256 = XmlHelper.CalculateSha256(combined);
        }
    }
    public class Element : BaseObject
    {
        public string Type = "Element";

        public string Name { get; }
        public string Sha256 { get; }

        public List<Attribute> Attributes { get; }
        public List<dynamic> Children { get; }
        public Element(string name, List<Attribute> attributes, List<object> children)
        {
            this.Name = name;
            this.Attributes = attributes;
            Children = children;
            List<string> listOfChildrenSha256 = children.Select(x=>(string)((dynamic)x).Sha256).ToList();
            List<string> listOfAttributeSha256 = attributes.Select(x => (string)((dynamic)x).Sha256).ToList();
            var combinedList = new List<string> { name };
            combinedList.AddRange(listOfAttributeSha256);
            combinedList.AddRange(listOfChildrenSha256);
            var combined = string.Join(",", combinedList);
            this.Sha256 = XmlHelper.CalculateSha256(combined);
        }
    }

    public class CharacterData
    {
        public string Type = "CharacterData";

        public string Content { get; }
        public string Sha256 { get; }
        public CharacterData(string content)
        {
            this.Content = content;
            this.Sha256 = XmlHelper.CalculateSha256(content);
        }

    }

}
